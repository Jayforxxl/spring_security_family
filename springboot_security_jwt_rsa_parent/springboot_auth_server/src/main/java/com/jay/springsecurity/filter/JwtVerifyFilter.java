package com.jay.springsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jay.springsecurity.config.RsaKeyConfig;
import com.jay.springsecurity.domain.SysUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import springboot.common.domain.Payload;
import springboot.common.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 身份校验
 *
 * @author ZhangJieChao
 * @version 1.0
 * @date 2020/8/1 16:28
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyConfig rsaKeyConfig;

    public JwtVerifyFilter(AuthenticationManager authenticationManager,RsaKeyConfig rsaKeyConfig) {
        super(authenticationManager);
        this.rsaKeyConfig = rsaKeyConfig;
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {//如果没有登录给用户提示请登录
            //继续执行后续过滤器
            chain.doFilter(request, response);
            //返回提示信息
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code",HttpServletResponse.SC_FORBIDDEN);
            resultMap.put("msg","请先认证");
            out.write(new ObjectMapper().writeValueAsString(resultMap));
            out.flush();
            out.close();
        } else { //如果携带了正确格式的token
            String token = header.replace("Bearer ","");
            //验证token是否正确
            Payload<SysUser> payload = JwtUtils.getInfoFromToken(token, rsaKeyConfig.getPublicKey(), SysUser.class);
            SysUser user = payload.getUserInfo();
            if (user != null) {
                UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
                //将用户信息放入到token中
                SecurityContextHolder.getContext().setAuthentication(authResult);
                //继续执行后续过滤器
                chain.doFilter(request, response);
            }
        }
    }

}
