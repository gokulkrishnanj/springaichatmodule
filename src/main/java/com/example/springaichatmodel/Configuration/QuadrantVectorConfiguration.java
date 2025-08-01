package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QuadrantVectorConfiguration {

    private QdrantClient qdrantClient;

    public QuadrantVectorConfiguration(QdrantClient qdrantClient) {
        this.qdrantClient = qdrantClient;
    }

//    @Bean
//    public QdrantVectorStore qdrantVectorStore(QdrantClient qdrantClient. Em){
//
//    }
//
//    @Bean
//    public QdrantEm
}
