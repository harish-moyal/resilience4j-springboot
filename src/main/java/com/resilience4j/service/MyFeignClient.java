package com.resilience4j.service;

import feign.RequestLine;
import org.springframework.web.bind.annotation.GetMapping;

public interface MyFeignClient {

    @RequestLine("GET /sample")
    String feignMessage();

}
