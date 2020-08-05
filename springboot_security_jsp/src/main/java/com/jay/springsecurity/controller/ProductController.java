package com.jay.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Secured("ROLE_USER") //这里一定是ROLE_USER不能是USER
    @RequestMapping("/findAll")
    public String findAll(){
        return "product-list";
    }
}
