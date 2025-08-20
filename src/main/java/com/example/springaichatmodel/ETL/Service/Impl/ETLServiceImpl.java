package com.example.springaichatmodel.ETL.Service.Impl;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.ETL.Helper.ETLHelper;
import com.example.springaichatmodel.ETL.Service.ETLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ETLServiceImpl implements ETLService {
    private static final Logger log = LoggerFactory.getLogger(ETLServiceImpl.class);
    private ETLHelper ETLhelper;

    ETLServiceImpl(ETLHelper ETLhelper) {
        this.ETLhelper = ETLhelper;
    }

    @Override
    public ResponseMessageDTO extractEmbeddingDataFromString(List<String> stringList) {
        return ETLhelper.loadEmbeddingDataIntoVectorStore(stringList);
//        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
//        if (!stringList.isEmpty()) {
//            List<Document> documentList = new ArrayList<>();
//            stringList.forEach((e -> documentList.add(new Document(e))));
//            vectorStore.add(documentList);
//            responseMessageDTO.setMessage("Added to vector store.");
//            return responseMessageDTO;
//        }
//        responseMessageDTO.setMessage("Error while adding to vector store");
//        return ;
    }

    @Override
    public ResponseMessageDTO extractEmbeddingDataFromDocument(MultipartFile file) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        Resource resource = file.getResource();
        return ETLhelper.loadEmbeddingDataIntoVectorStore(resource);
        // document reader (Extract)
//        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
//        List<Document> documentList = tikaDocumentReader.read();
//        TokenTextSplitter textSplitter = new TokenTextSplitter();
//        textSplitter.apply(documentList);
//        DocumentWriter
//        try {
//            if (!documentList.isEmpty()){
//                vectorStore.add(documentList);
//                responseMessageDTO.setMessage("Added to vector store.");
//                return responseMessageDTO;
//            }
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//        responseMessageDTO.setMessage("Error while adding to vector store");
//        return responseMessageDTO;
    }
}