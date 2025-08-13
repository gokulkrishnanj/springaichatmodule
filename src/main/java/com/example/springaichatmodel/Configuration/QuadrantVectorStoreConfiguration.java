package com.example.springaichatmodel.Configuration;

import com.knuddels.jtokkit.api.EncodingType;
import io.qdrant.client.QdrantClient;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
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
    public VectorStore vectorStore(QdrantClient qdrantClient, OllamaEmbeddingModel ollamaEmbeddingModel) {
        return QdrantVectorStore
                .builder(qdrantClient, ollamaEmbeddingModel)
                .collectionName(qdrantCollectionName)
                .initializeSchema(initializeSchema)
                .batchingStrategy(new TokenCountBatchingStrategy(EncodingType.CL100K_BASE, 100000,0.1))
                .build();
    }

}
