package com.example.springaichatmodel.ETL.Configuration;

import com.example.springaichatmodel.Model.UserVectorEmbeddings;
import com.example.springaichatmodel.Repository.UserVectorEmbeddingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Slf4j
public class CustomVectorStore implements VectorStore {

    private JdbcTemplate jdbcTemplate;

    private EmbeddingModel embeddingModel;

    private UserVectorEmbeddingsRepository userVectorEmbeddingsRepository;

    public CustomVectorStore(JdbcTemplate jdbcTemplate, UserVectorEmbeddingsRepository userVectorEmbeddingsRepository, EmbeddingModel embeddingModel) {
        this.jdbcTemplate = jdbcTemplate;
        this.userVectorEmbeddingsRepository = userVectorEmbeddingsRepository;
        this.embeddingModel = embeddingModel;
    }


    public CustomVectorStore(VectorStoreBuilder vectorStoreBuilder) {
        this.userVectorEmbeddingsRepository = vectorStoreBuilder.userVectorEmbeddingsRepository;
        this.embeddingModel = vectorStoreBuilder.embeddingModel;
        this.jdbcTemplate = vectorStoreBuilder.jdbcTemplate;
    }


    @Override
    public void add(List<Document> documents) {
        log.info("Inside the custom vector store && Adding embeddings to the vector DB.");
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for(Document document : documents){
            UserVectorEmbeddings userVectorEmbeddings = new UserVectorEmbeddings();
            float [] embeds = embeddingModel.embed(document);
            userVectorEmbeddings.setUserId(userId);
            userVectorEmbeddings.setContent(document.getText());
            userVectorEmbeddings.setMetaData(document.getMetadata());
            userVectorEmbeddings.setEmbeddings(embeds);
            userVectorEmbeddingsRepository.save(userVectorEmbeddings);
        }
    }

    @Override
    public void delete(List<String> idList) {

    }

    @Override
    public void delete(Filter.Expression filterExpression) {

    }

    @Override
    public List<Document> similaritySearch(SearchRequest request) {
        log.info("Inside the custom vector store && Similarity Search.");
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        float [] embedding = embeddingModel.embed(request.getQuery());
        int topK = request.getTopK();
        List<UserVectorEmbeddings> userVectorEmbeddingsList = userVectorEmbeddingsRepository.searchSimilar(userId,
                embedding,
                topK);
        return userVectorEmbeddingsList
                .stream()
                .map(userVectorEmbeddings -> {
                    return new Document(userVectorEmbeddings.getContent(), userVectorEmbeddings.getMetaData());
                }).toList();
    }

    @Override
    public List<Document> similaritySearch(String query) {
        return VectorStore.super.similaritySearch(query);
    }


    // static method to get the instance of VectorStoreBuilder
    public static VectorStoreBuilder builder(){
        return new VectorStoreBuilder();
    }

    public static class VectorStoreBuilder{
        private EmbeddingModel embeddingModel;
        private JdbcTemplate jdbcTemplate;
        private UserVectorEmbeddingsRepository userVectorEmbeddingsRepository;

        public VectorStoreBuilder embeddingModel(EmbeddingModel embeddingModel){
            this.embeddingModel = embeddingModel;
            return this;
        }

        public VectorStoreBuilder jdbcTemplate(JdbcTemplate jdbcTemplate){
            this.jdbcTemplate = jdbcTemplate;
            return this;
        }

        public VectorStoreBuilder vectorStoreRespository(UserVectorEmbeddingsRepository userVectorEmbeddingsRepository){
            this.userVectorEmbeddingsRepository = userVectorEmbeddingsRepository;
            return this;
        }

        public CustomVectorStore build(){
            return new CustomVectorStore(this);
        }

    }

}
