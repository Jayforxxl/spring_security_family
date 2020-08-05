package com.jay.springsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jay.springsecurity.config.RsaKeyConfig;
import com.jay.springsecurity.domain.SysRole;
import com.jay.springsecurity.domain.SysUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springboot.common.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户认证
 *
 * @author ZhangJieChao
 * @version 1.0
 * @date 2020/8/1 15:08
 */
public class JWTLLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private RsaKeyConfig rsaKeyConfig;

    public JWTLLoginFilter(AuthenticationManager authenticationManager, RsaKeyConfig rsaKeyConfig) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyConfig = rsaKeyConfig;
    }

    /**
     * 将用户提交表单的方式改为AJAX
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SysUser sysUser = (SysUser)new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        }catch (Exception e){
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map resultMap = new HashMap();
                resultMap.put("code",HttpServletResponse.SC_UNAUTHORIZED);
                resultMap.put("msg","用户名或密码错误");
                out.write(new ObjectMapper().writeValueAsString(resultMap));
                out.flush();
                out.close();
            }catch (Exception outEx){
                outEx.printStackTrace();
            }
            throw new RuntimeException();
        }
    }

    /**
     * 用户提交用户+密码成功后返回token
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(authResult.getName());
        sysUser.setRoles((List<SysRole>)authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(sysUser,rsaKeyConfig.getPrivateKey(),24*60);
        response.addHeader("Authorization","Bearer "+token);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code",HttpServletResponse.SC_UNAUTHORIZED);
            resultMap.put("msg","认证通过");
            out.write(new ObjectMapper().writeValueAsString(resultMap));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
