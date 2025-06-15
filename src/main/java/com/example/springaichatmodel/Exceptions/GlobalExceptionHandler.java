package com.example.springaichatmodel.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> runTimeException(Exception e){
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error",e.getLocalizedMessage());
        return new ResponseEntity<>(errorResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
