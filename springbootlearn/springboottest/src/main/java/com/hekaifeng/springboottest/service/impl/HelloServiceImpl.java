package com.hekaifeng.springboottest.service.impl;

import com.hekaifeng.springboottest.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "sayHello" + name;
    }
}
