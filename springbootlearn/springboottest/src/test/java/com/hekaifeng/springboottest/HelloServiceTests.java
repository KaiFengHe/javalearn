package com.hekaifeng.springboottest;

import com.hekaifeng.springboottest.service.HelloService;
import com.hekaifeng.springboottest.service.impl.HelloServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
public class HelloServiceTests {


    @Resource
    private HelloService helloService;

    @TestConfiguration
    static class prepareCustomServices {
        @Bean
        public HelloService getHelloService() {
            return new HelloServiceImpl();
        }

    }

    @Test
    public void testExample1() throws Exception {
        String hkf = helloService.sayHello("hkf");
        Assert.assertEquals("sayHellohkf", hkf);
    }


}