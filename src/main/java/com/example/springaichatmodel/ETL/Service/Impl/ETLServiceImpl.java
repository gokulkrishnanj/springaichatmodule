package com.example.springaichatmodel.ETL.Service.Impl;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.ETL.Service.ETLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ETLServiceImpl implements ETLService {
    private static final Logger log = LoggerFactory.getLogger(ETLServiceImpl.class);
    private VectorStore vectorStore;

    ETLServiceImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public ResponseMessageDTO extractEmbeddingDataFromString(List<String> stringList) {
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
    public ResponseMessageDTO extractEmbeddingDataFromDocument(MultipartFile file) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        Resource resource = file.getResource();
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        List<Document> documentList = tikaDocumentReader.get();
        try {
            if (!documentList.isEmpty()){
                vectorStore.add(documentList);
                responseMessageDTO.setMessage("Added to vector store.");
                return responseMessageDTO;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        responseMessageDTO.setMessage("Error while adding to vector store");
        return responseMessageDTO;
    }
}