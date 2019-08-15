package com.resilience4j.controller;

import com.resilience4j.service.MyFeignClient;
import com.resilience4j.service.Resilience4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resilience4j")
public class Resilience4jController {

    @Autowired
    private Resilience4jService resilience4jService;

    @Autowired
    private MyFeignClient myFeignClient;

    @GetMapping(path = "/{type}")
    public String helloWorld(@PathVariable("type") String type) {
        return resilience4jService.helloWorld(type);
    }

    @GetMapping(path = "/callOtherService")
    public String callOtherService(@RequestParam(required = false) String success) {
        return resilience4jService.callOtherService(success);
    }

    @GetMapping(path = "/feignClient")
    public String feign() {
        return myFeignClient.feignMessage();
    }

    @GetMapping(path = "/errorAlways")
    public String errorAlways() {
        return resilience4jService.alwaysError();
    }
}
