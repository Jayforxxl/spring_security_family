package com.jay.springsecurity.config;

import com.jay.springsecurity.filter.JwtVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity //获得SpringSecurity配置信息(需要继承WebSecurityConfigurerAdapter类来修改配置)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    //配置SpringSecurity相关信息
    public void configure(HttpSecurity http) throws Exception {
        //  释放静态资源 + 指定资源拦截规则 + 指定自定义认证页面 + 指定退出认证配置 + csrf配置
        //  需要以http.authorizeRequests()作为配置开始
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("USER","PRODUCTER","ORDER")//Spring Security默认前缀为ROLE_
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtVerifyFilter(super.authenticationManager(),rsaKeyConfig))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//分布式中Session不起作用
    }




}
