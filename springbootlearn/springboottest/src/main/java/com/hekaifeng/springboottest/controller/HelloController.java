package com.hekaifeng.springboottest.controller;

import com.hekaifeng.springboottest.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private HelloService helloService;

    @GetMapping("/sayHello")
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }
}
