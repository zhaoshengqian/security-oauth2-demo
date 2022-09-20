package com.security.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResourceController {

    @GetMapping("/resource")
    public Principal resource(Principal principal){
        return principal;
    }

    @GetMapping("/hello")
    public String hello(String params){
        return "你好："+params;
    }

    @PreAuthorize("#oauth2.hasScope('p2')")
    @GetMapping("/phone")
    public String phone(){
        return "13131313131";
    }

}
