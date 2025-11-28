package com.example.springaichatmodel.Repository;

import com.example.springaichatmodel.Model.UserVectorEmbeddings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVectorEmbeddingsRepository extends JpaRepository<UserVectorEmbeddings, Long> {

}
