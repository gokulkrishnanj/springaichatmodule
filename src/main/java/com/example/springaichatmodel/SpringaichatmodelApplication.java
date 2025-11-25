package com.example.springaichatmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringaichatmodelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringaichatmodelApplication.class, args);
        System.out.println("Welcome to SpringAI");
    }

}
