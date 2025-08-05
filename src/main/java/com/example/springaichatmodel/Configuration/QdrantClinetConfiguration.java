package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantClinetConfiguration {

    @Value("${spring.ai.vectorstore.qdrant.host}")
    private String qdrantHost;

    @Value("${spring.ai.vectorstore.qdrant.port}")
    private int qdrantPort;

    @Value("${spring.ai.vectorstore.qdrant.api-key}")
    private String qdrantAPIKey;

    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder qdrantGRPCClientBuilder = QdrantGrpcClient
                .newBuilder(qdrantHost, qdrantPort, false);
        qdrantGRPCClientBuilder.withApiKey(qdrantAPIKey);
        return new QdrantClient(qdrantGRPCClientBuilder.build());
    }

    @Bean
    public OllamaApi ollamaApi() {
        return OllamaApi
                .builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    @Bean
    public OllamaEmbeddingModel ollamaEmbeddingModel(OllamaApi ollamaApi) {
        return OllamaEmbeddingModel
                .builder()
                .ollamaApi(ollamaApi)
                .build();
    }
}
