package com.resilience4j.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@Service
public class Resilience4jService {

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "helloWorld", fallbackMethod = "errorInternal")
    public String helloWorld(String type) {
        if (type.equals("error")) {
            System.out.println("1. Call received means circuit is closed");
            throw new RuntimeException("This is runtime exception");
        } else {
            return "Hello world from Resilience4jService";
        }
    }

    @CircuitBreaker(name = "callOtherService", fallbackMethod = "errorExternal")
    public String callOtherService(String success) {
        System.out.println("2. Call received means circuit is closed");
        if (Objects.nonNull(success)) {
            return restTemplate.getForObject(URI.create("http://localhost:8090/sample?success="+success), String.class);
        }
        return restTemplate.getForObject(URI.create("http://localhost:8090/sample"), String.class);
    }

    public String alwaysError() {
        throw new RuntimeException("I will always throw error");
    }

    public String errorInternal(String methodParam, Throwable throwable) {
        return "This is error from fallback triggered by internal error";
    }

    public String errorExternal(Throwable throwable) {
        return "This is error from fallback triggered by external error";
    }
}
