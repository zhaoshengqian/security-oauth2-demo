package com.security.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello world!";
    }

    @RequestMapping("/r/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1(){
        return "访问资源1!";
    }
    @RequestMapping("/r/r2")
    @PreAuthorize("hasAuthority('p2')")
    public String r2(){
        return "访问资源2!";
    }

    @RequestMapping(value = "/login-success",produces = "text/plain;charset=UTF-8")
    public String loginSuccess(HttpServletRequest req){
        System.out.println("登录成功");
        return "登录成功";
    }

}
