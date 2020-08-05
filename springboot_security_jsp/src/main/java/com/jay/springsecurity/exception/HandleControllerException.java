package com.jay.springsecurity.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleControllerException {
    @ExceptionHandler(RuntimeException.class)
    public String exceptionHandler(RuntimeException e){
        if(e instanceof AccessDeniedException){
            //如果是权限不足异常，则跳转到权限不足页面！
            return "redirect:/403.jsp";
        }else {
            //其余的异常都到500页面！
            return "redirect:/500.jsp";
        }
    }
}
