package com.example.springaichatmodel.Configuration;

import io.qdrant.client.QdrantClient;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QuadrantVectorStoreConfiguration {

    @Value("${spring.ai.vectorstore.qdrant.collectionname}")
    private String qdrantCollectionName;

    @Value("${spring.ai.vectorstore.qdrant.initialize-schema}")
    private boolean initializeSchema;

    @Bean
    public VectorStore vectorStore(QdrantClient qdrantClient, OpenAiEmbeddingModel openAiEmbeddingModel) {
        return QdrantVectorStore
                .builder(qdrantClient, openAiEmbeddingModel)
                .collectionName(qdrantCollectionName)
                .initializeSchema(initializeSchema)
                .batchingStrategy(new TokenCountBatchingStrategy())
                .build();
    }

}
