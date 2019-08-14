package com.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringBootResilienceDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootResilienceDemo.class, args);

        /*CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");
        //RateLimiter rateLimiter = RateLimiter.ofDefaults("backendName");
        FeignDecorators decorators = FeignDecorators.builder()
                //.withRateLimiter(rateLimiter)
                .withCircuitBreaker(circuitBreaker)
                .build();
        MyFeignClient myService = Resilience4jFeign.builder(decorators).contract(new SpringMvcContract())
                .target(MyFeignClient.class, "http://localhost:8080/");*/
    }
}
