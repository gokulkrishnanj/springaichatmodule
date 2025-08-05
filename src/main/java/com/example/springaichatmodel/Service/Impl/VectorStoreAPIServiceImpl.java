package com.example.springaichatmodel.Service.Impl;

import com.example.springaichatmodel.Service.VectorStoreAPIService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VectorStoreAPIServiceImpl implements VectorStoreAPIService {

    private VectorStore vectorStore;

    VectorStoreAPIServiceImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void addDocumentsToVector(List<String> documents) {
        List<Document> documentList = new ArrayList<>();
        documents.forEach((element) -> {
            documentList.add(new Document(element));
        });
        vectorStore.add(documentList);
    }
}
