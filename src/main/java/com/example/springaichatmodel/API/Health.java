package com.example.springaichatmodel.API;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "${api/v1}")
public interface Health {
    @GetMapping(value = "${health}")
    public ResponseEntity<String> getHealth();
}
