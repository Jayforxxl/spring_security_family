package com.jay.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    //@Secured("ROLE_USER") //这里一定是ROLE_USER不能是USER
    @RequestMapping("/findAll")
    public String findAll(){
        return "产品列表查询成功";
    }
}
