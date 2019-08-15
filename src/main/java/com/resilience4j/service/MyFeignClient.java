package com.resilience4j.service;

import org.springframework.web.bind.annotation.GetMapping;

public interface MyFeignClient {

    @GetMapping(path = "/sample")
    String feignMessage();

}
