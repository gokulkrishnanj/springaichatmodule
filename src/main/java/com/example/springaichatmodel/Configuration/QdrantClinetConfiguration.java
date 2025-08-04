package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
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

    @Value("${spring.ai.openai.api-key}")
    private String openAiAPIKey;

    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder qdrantGRPCClientBuilder = QdrantGrpcClient
                .newBuilder(qdrantHost, qdrantPort, false);
        qdrantGRPCClientBuilder.withApiKey(qdrantAPIKey);
        return new QdrantClient(qdrantGRPCClientBuilder.build());
    }

    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi
                .builder()
                .apiKey(openAiAPIKey)
                .build();
    }

    @Bean
    public OpenAiEmbeddingModel openAiEmbeddingModel(OpenAiApi openAiApi) {
        OpenAiEmbeddingOptions openAiEmbeddingOptions = OpenAiEmbeddingOptions
                .builder()
                .model("text-embedding-3-small")
                .build();
        return new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED, openAiEmbeddingOptions);
    }
}
