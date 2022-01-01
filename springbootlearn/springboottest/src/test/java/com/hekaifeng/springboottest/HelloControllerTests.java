package com.hekaifeng.springboottest;

import com.hekaifeng.springboottest.controller.HelloController;
import com.hekaifeng.springboottest.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
class HelloControllerTests {

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private HelloService helloService;
//
//    @Test
//    void testExample() throws Exception {
//        given(this.helloService.sayHello("hkf"))
//                .willReturn("sayMockHello");
//        this.mvc.perform(get("/sayHello").param("name","hkf").accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk()).andExpect(content().string("sayMockHello"));
//    }

    @Test
    public void testExample1() throws Exception {
        this.mvc.perform(get("/sayHello").param("name", "hkf").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string("sayHelloHkf"));
    }

}