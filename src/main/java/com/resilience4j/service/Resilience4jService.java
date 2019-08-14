package com.resilience4j.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service

public class Resilience4jService {

    //@CircuitBreaker(name = "resilience4jService", fallbackMethod = "error")
    public String helloWorld(String type) {
        if (type.equals("error")) {
            System.out.println("Call received means circuit is closed");
            throw new RuntimeException("This is runtime exception");
        } else {
            return "Hello world from Resilience4jService";
        }
    }

    public String alwaysError() {
        throw new RuntimeException("I will always throw error");
    }

    public String error(String message, Throwable throwable) {
        return "This is error from fallback";
    }
}
