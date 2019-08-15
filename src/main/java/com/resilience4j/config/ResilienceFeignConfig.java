package com.resilience4j.config;

import com.resilience4j.service.MyFeignClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * When Feign client is used as a http communication library we need to define the Feign client bean wrapped inside the CircuitBreaker decorator
 * The circuit breaker decorator will be applied whenever any method of this client is called.
 */
@Configuration
public class ResilienceFeignConfig {

    @Bean
    public MyFeignClient myFeignClient(CircuitBreakerRegistry registry) {

        CircuitBreaker circuitBreaker = registry.circuitBreaker("myFeignClient");

        MyFeignClient requestFailedFallback = () -> "Fallback on FeignException";

        MyFeignClient circuitBreakerFallback = () -> "CircuitBreaker is open!";

        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                //.withFallbackFactory(MyFallback::new)
                .withFallback(requestFailedFallback, FeignException.class)
                .withFallback(circuitBreakerFallback, CallNotPermittedException.class)
                .build();

        return Resilience4jFeign.builder(decorators)
                // This is needed to use Spring mvc annotations on feign client
                .contract(new SpringMvcContract())
                .target(MyFeignClient.class, "http://localhost:8090/");
    }

    public class MyFallback implements MyFeignClient {

        private Exception exception;

        public MyFallback(Exception e) {
            this.exception = e;
        }

        @Override
        public String feignMessage() {
            return "This is from fallback";
        }
    }
}
