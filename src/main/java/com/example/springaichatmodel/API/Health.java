package com.example.springaichatmodel.API;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "${api/v1/chat}")
public interface Health {

    //health API
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "${health}")
    public ResponseEntity<String> getHealth();
}
