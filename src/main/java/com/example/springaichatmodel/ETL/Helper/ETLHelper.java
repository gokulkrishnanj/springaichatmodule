package com.example.springaichatmodel.ETL.Helper;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ETLHelper {

    //DocumentReader (Extract)
    private TikaDocumentReader tikaDocumentReader;

    //TokenSplitter (Transform)
    private TokenTextSplitter tokenTextSplitter;

    //Write to db (Load)
    private VectorStore vectorStore;

    public ETLHelper(TikaDocumentReader tikaDocumentReader, TokenTextSplitter tokenTextSplitter, VectorStore vectorStore){
        this.tikaDocumentReader = tikaDocumentReader;
        this.tokenTextSplitter = tokenTextSplitter;
        this.vectorStore = vectorStore;
    }

    public ResponseMessageDTO loadEmbeddingDataIntoVectorStore(List<String> stringList){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        try {
            if (!stringList.isEmpty()){
                List<Document> documentList = new ArrayList<>();
                stringList.forEach(string -> documentList.add(new Document(string)));
                tokenTextSplitter.apply(documentList);
                vectorStore.add(documentList);
                responseMessageDTO.setMessage("Data added to the DB.");
            }
            else {
                responseMessageDTO.setMessage("Empty document passed/ Nothing to embed and add to  the DB.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseMessageDTO;
    }

    public ResponseMessageDTO loadEmbeddingDataIntoVectorStore(Resource resource){
        List<Document> documentList = tikaDocumentReader.get();
        tokenTextSplitter.apply(documentList);
        ResponseMessageDTO responseMessageDTO= new ResponseMessageDTO();
        try {
            if (!documentList.isEmpty()){
                vectorStore.add(documentList);
                responseMessageDTO.setMessage("Data added to the DB.");
            }
            else {
                responseMessageDTO.setMessage("Empty document passed/ Nothing to embed and add to  the DB.");
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        return  responseMessageDTO;
    }
}
