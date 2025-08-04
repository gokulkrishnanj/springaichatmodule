package com.example.springaichatmodel.Configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

    @Value("${cloudinary.cloud-name}")
    private String cloud_name;

    @Value("${cloudinary.api-key}")
    private String api_key;

    @Value("${cloudinary.api-secret}")
    private String api_secret;

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, String> cloudinaryConfigMap = new HashMap();
        System.out.println("cloudinary bean creation");
        cloudinaryConfigMap.put("cloud_name", cloud_name);
        cloudinaryConfigMap.put("api_key", api_key);
        cloudinaryConfigMap.put("api_secret", api_secret);
        return new Cloudinary(cloudinaryConfigMap);
    }

}
