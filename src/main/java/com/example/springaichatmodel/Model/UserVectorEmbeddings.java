package com.example.springaichatmodel.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "user_vector_embeddings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVectorEmbeddings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Column(length = -1) // to store max character in db default is 255 if -1 max
    private String content;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metaData;

    @Column(columnDefinition = "vector(1024)")
    private float[] embeddings;

}
