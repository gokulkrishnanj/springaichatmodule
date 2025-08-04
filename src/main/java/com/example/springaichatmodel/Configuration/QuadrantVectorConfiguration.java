package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QuadrantVectorConfiguration {

    @Value("${spring.ai.vectorstore.qdrant.collectionname}")
    private String qdrantCollectionName;

    @Value("${spring.ai.openai.api-key}")
    private String embeddingAPIKey;

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
//    public OpenAiEmbeddingModel openAiEmbeddingModel(){
//        return new OpenAiEmbeddingModel(new OpenAiApi());
//    }
}
