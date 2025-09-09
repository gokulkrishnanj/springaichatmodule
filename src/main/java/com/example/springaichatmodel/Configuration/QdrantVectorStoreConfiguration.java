package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class QdrantVectorStoreConfiguration {

    @Value("${spring.ai.vectorstore.qdrant.host}")
    private String qdrantHost;

    @Value("${spring.ai.vectorstore.qdrant.port}")
    private int qdrantPort;

    @Value("${spring.ai.vectorstore.qdrant.api-key}")
    private String qdrantAPIKey;

    @Value("${spring.ai.vectorstore.qdrant.use-tls}")
    private boolean useTransportLayerSecurity;

    @Bean
    @Lazy
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder qdrantGRPCClientBuilder = QdrantGrpcClient
                .newBuilder(qdrantHost, qdrantPort, useTransportLayerSecurity);
        qdrantGRPCClientBuilder.withApiKey(qdrantAPIKey);
        return new QdrantClient(qdrantGRPCClientBuilder.build());
    }

}
