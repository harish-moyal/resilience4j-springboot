package com.resilience4j.config;

import com.resilience4j.service.MyFeignClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * When Feign client is used as a http communication library we need to define the Feign client bean wrapped inside the CircuitBreaker decorator
 * The circuit breaker decorator will be applied whenever any method of this client is called.
 */
//@Configuration
public class ResilienceFeignConfig_Backup {

    @Autowired
    CircuitBreakerProperties circuitBreakerProperties;

    @Autowired
    CircuitBreakerRegistry registry;

    @Bean
    public MyFeignClient myFeignClient() {

        //RateLimiter rateLimiter = RateLimiter.ofDefaults("backendName");
        //MyFeignClient requestFailedFallback = () -> "fallback from feign fallback";

        /* This is not needed if we pull the CircuitBreaker from the registry. Just define the config into application.yml
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .ringBufferSizeInClosedState(4)
                .failureRateThreshold(50)
                .ringBufferSizeInHalfOpenState(2)
                .waitDurationInOpenState(Duration.ofSeconds(20))
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("myFeignClient", circuitBreakerConfig);*/

        CircuitBreaker circuitBreaker = registry.circuitBreaker("myFeignClient");

        FeignDecorators decorators = FeignDecorators.builder()
                //.withRateLimiter(rateLimiter) // Not looking into it right now
                .withCircuitBreaker(circuitBreaker)
                .withFallbackFactory(MyFallback::new)
                //.withFallback(requestFailedFallback) // Does not works, ticket raised https://github.com/resilience4j/resilience4j/issues/560
                .build();
        return Resilience4jFeign.builder(decorators)
                //.contract(new SpringMvcContract()) // This is only needed when we use @GetMapping in the feign client
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
