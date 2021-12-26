package com.hekaifeng.springboothello;

import io.spring.initializr.generator.project.ProjectGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        ProjectGenerator projectGenerator = new ProjectGenerator();
        return "hello";
    }
}
