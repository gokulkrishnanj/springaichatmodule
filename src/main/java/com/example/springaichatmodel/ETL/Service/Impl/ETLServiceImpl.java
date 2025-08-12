package com.example.springaichatmodel.ETL.Service.Impl;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.ETL.Service.ETLService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ETLServiceImpl implements ETLService {
    private VectorStore vectorStore;

    ETLServiceImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public ResponseMessageDTO extractDataFromString(List<String> stringList) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (!stringList.isEmpty()) {
            List<Document> documentList = new ArrayList<>();
            stringList.forEach((e -> documentList.add(new Document(e))));
            vectorStore.add(documentList);
            responseMessageDTO.setMessage("Added to vector store.");
            return responseMessageDTO;
        }
        responseMessageDTO.setMessage("Error while adding to vector store");
        return responseMessageDTO;
    }

    @Override
    public ResponseMessageDTO extractDataFromDocument(MultipartFile file) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        return responseMessageDTO;
    }
}