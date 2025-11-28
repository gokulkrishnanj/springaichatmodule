package com.example.springaichatmodel.Repository;

import com.example.springaichatmodel.Model.UserVectorEmbeddings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVectorEmbeddingsRepository extends JpaRepository<UserVectorEmbeddings, Long> {
    @Query(value = """
    SELECT * FROM uservectorembeddings
    WHERE user_id = :userId
    ORDER BY embeddings <-> cast(:embedding as vector)
    LIMIT :topK
""", nativeQuery = true)
    List<UserVectorEmbeddings> searchSimilar(
            @Param("userId") String userId,
            @Param("embedding") float[] embedding,
            @Param("topK") int topK);

}
