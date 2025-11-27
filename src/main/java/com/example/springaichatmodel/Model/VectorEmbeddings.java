//package com.example.springaichatmodel.Model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import com.pgvector.PGvector;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class VectorEmbeddings {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String userId;
//
//    @Column(columnDefinition = "jsonb")
//    private String metaData;
//
//    @Column(columnDefinition = "vector(1536)")
//    private PGvector vector;
//
//}
