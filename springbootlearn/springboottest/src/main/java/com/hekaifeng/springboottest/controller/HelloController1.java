package com.hekaifeng.springboottest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController1 {


    @GetMapping("/sayHello1")
    public String sayHello() {
        return "sayHello1";
    }
}
