package com.example.springaichatmodel.Controller;

import com.example.springaichatmodel.API.Health;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class HealthController implements Health {
    public ResponseEntity<String> getHealth() {
        log.info("Health Controller");
        return new ResponseEntity<>("Health", HttpStatus.OK);
    }
}
