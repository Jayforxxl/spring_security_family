package com.jay.springsecurity.config;

import com.jay.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //获得SpringSecurity配置信息(需要继承WebSecurityConfigurerAdapter类来修改配置)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入自定义的UserDetailsService实现类
     * 代表认证用户的来源使用其接口方法loadUserByUsername中返回的UserDetails
     */
    @Autowired
    private UserService userService;

    /**
     * 密码加密器BCrypt
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //认证用户的来源(内存or数据库)
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在配置类中配置SpringSecurity指定角色要求不能加前缀
        //auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("USER");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    //配置SpringSecurity相关信息
    public void configure(HttpSecurity http) throws Exception {
        //  释放静态资源 + 指定资源拦截规则 + 指定自定义认证页面 + 指定退出认证配置 + csrf配置
        //  需要以http.authorizeRequests()作为配置开始
        http.authorizeRequests().antMatchers("/login.jsp","/failer.jsp","/css/**","/img/**","plugins/**").permitAll()
                .antMatchers("/**").hasAnyRole("USER","PRODUCTER","ORDER")//Spring Security默认前缀为ROLE_
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.jsp").loginProcessingUrl("/login").successForwardUrl("/index.jsp").failureForwardUrl("/failer.jsp").permitAll() //这里的permit的只有/login.jsp
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login.jsp").invalidateHttpSession(true).permitAll()//这里的permit的只有/logout
                .and()
                .csrf().disable();
    }
}
