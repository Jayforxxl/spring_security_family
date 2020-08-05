package com.jay.springsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义Service需要实现UserDetailsService接口才能被SpringSecurity识别
 */
public interface UserService extends UserDetailsService {}
