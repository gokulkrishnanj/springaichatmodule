package com.example.springaichatmodel.Controller;

import com.example.springaichatmodel.API.Health;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController implements Health {
    public ResponseEntity<String> getHealth() {
        return new ResponseEntity<>("Health", HttpStatus.OK);
    }
}
