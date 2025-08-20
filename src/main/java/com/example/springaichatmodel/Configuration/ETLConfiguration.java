package com.example.springaichatmodel.Configuration;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ETLConfiguration {

    @Bean
    @Lazy
    public TokenTextSplitter myTokenTextSplitter(){
        return new TokenTextSplitter();
    }
}
