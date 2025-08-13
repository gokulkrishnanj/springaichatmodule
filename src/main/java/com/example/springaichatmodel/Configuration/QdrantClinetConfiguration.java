package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class QdrantClinetConfiguration {

    @Value("${spring.ai.vectorstore.qdrant.host}")
    private String qdrantHost;

    @Value("${spring.ai.vectorstore.qdrant.port}")
    private int qdrantPort;

    @Value("${spring.ai.vectorstore.qdrant.api-key}")
    private String qdrantAPIKey;

    @Value("${spring.ai.ollama.embedding.model}")
    private String ollamaEmbeddingModel;


    @Bean
    @Lazy
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder qdrantGRPCClientBuilder = QdrantGrpcClient
                .newBuilder(qdrantHost, qdrantPort, false);
        qdrantGRPCClientBuilder.withApiKey(qdrantAPIKey);
        return new QdrantClient(qdrantGRPCClientBuilder.build());
    }

    @Bean
    @Lazy
    public OllamaApi ollamaApi() {
        return OllamaApi
                .builder()
                .baseUrl("http://localhost:11434")
                .build();
    }
    @Bean
    @Lazy
    public OllamaOptions ollamaOptions(){
        return OllamaOptions
                .builder()
                .model(ollamaEmbeddingModel)
                .build();
    }

    @Bean
    public OllamaEmbeddingModel ollamaEmbeddingModel(OllamaApi ollamaApi, OllamaOptions ollamaOptions){
        return OllamaEmbeddingModel
                .builder()
                .defaultOptions(ollamaOptions)
                .ollamaApi(ollamaApi)
                .build();
    }
}
