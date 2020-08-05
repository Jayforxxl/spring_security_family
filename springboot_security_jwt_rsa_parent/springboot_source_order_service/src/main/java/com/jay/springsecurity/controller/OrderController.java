package com.jay.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Secured("ROLE_USER") //这里一定是ROLE_USER不能是USER
    @RequestMapping("/findAll")
    public String findAll(){
        return "订单列表查询成功";
    }
}
