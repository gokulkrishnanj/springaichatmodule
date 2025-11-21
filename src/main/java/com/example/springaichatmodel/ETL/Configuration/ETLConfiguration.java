package com.example.springaichatmodel.ETL.Configuration;

import com.knuddels.jtokkit.api.EncodingType;
import io.qdrant.client.QdrantClient;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ETLConfiguration {

    @Value("${spring.ai.vectorstore.qdrant.collectionname}")
    private String qdrantCollectionName;

    @Value("${spring.ai.vectorstore.qdrant.initialize-schema}")
    private boolean initializeSchema;

//    @Bean
//    @Lazy
//    public VectorStore vectorStore(QdrantClient qdrantClient, OllamaEmbeddingModel ollamaEmbeddingModel) {
//        return QdrantVectorStore
//                .builder(qdrantClient, ollamaEmbeddingModel)
//                .collectionName(qdrantCollectionName)
//                .initializeSchema(initializeSchema)
//                .batchingStrategy(new TokenCountBatchingStrategy(EncodingType.CL100K_BASE, Integer.MAX_VALUE, 0.1))
//                .build();
//    }

    @Bean
    @Lazy
    @Primary
    public PgVectorStore customPgVectorStore(DataSource dataSource, OllamaEmbeddingModel ollamaEmbeddingModel) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        PgVectorStore pgVectorStore = PgVectorStore.builder(jdbcTemplate,ollamaEmbeddingModel)
                .vectorTableName("user_embeddings")
                .dimensions(1024)
                .initializeSchema(true)
                .build();
        return pgVectorStore;
    }

    @Bean
    @Lazy
    public TokenTextSplitter tokenTextSplitter(){
        return new TokenTextSplitter();
    }
}
