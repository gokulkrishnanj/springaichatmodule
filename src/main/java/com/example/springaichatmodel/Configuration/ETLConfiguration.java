package com.example.springaichatmodel.Configuration;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class ETLConfiguration {

    @Bean
    @Lazy
    public TikaDocumentReader myDocumentReader( ){
        return new TikaDocumentReader();
    }

    @Bean
    @Lazy
    public TokenTextSplitter myTokenTextSplitter(){
        return new TokenTextSplitter();
    }
}
