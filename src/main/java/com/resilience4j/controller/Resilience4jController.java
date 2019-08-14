package com.resilience4j.controller;

import com.resilience4j.service.MyFeignClient;
import com.resilience4j.service.Resilience4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resilience4j")
public class Resilience4jController {

    @Autowired
    private Resilience4jService resilience4jService;

    @Autowired
    private MyFeignClient myFeignClient;

    @GetMapping
    @RequestMapping(path = "/{type}")
    public String helloWorld(@PathVariable("type") String type) {
        return resilience4jService.helloWorld(type);
    }

    @GetMapping
    @RequestMapping(path = "/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping
    @RequestMapping(path = "/feignClient")
    public String feign() {
        return myFeignClient.feignMessage();
    }

    @GetMapping
    @RequestMapping(path = "/errorAlways")
    public String errorAlways() {
        return resilience4jService.alwaysError();
    }
}
