package com.example.springaichatmodel.Exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> runTimeException(Exception e) {
        log.info("RuntimeException");
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("errorMessage", e.getLocalizedMessage());
        return new ResponseEntity<>(errorResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NonTransientAiException.class)
    public ResponseEntity<Map<String, String>> nonTransientAiException(Exception exception) {
        log.info("NonTransientAiException");
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("errorMessage", exception.getLocalizedMessage());
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }
}
