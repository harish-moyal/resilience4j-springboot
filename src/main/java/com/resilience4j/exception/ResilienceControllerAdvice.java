package com.resilience4j.exception;

import com.resilience4j.controller.Resilience4jController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice(assignableTypes = Resilience4jController.class)
public class ResilienceControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(io.github.resilience4j.circuitbreaker.CallNotPermittedException.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        ex.printStackTrace();
        return buildResponseEntity(INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus) {
        return status(httpStatus).body("{Circuit breaker is OPEN or HALF_OPEN}");
    }
}
