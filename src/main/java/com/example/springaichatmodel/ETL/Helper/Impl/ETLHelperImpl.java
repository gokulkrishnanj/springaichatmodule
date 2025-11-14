package com.example.springaichatmodel.ETL.Helper.Impl;

import com.example.springaichatmodel.ETL.Configuration.TikaDocumentReaderConfigHelper;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.ETL.Helper.ETLHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ETLHelperImpl implements ETLHelper {

    private static final Logger log = LogManager.getLogger(ETLHelperImpl.class);
    //TokenSplitter (Transform)
    private TokenTextSplitter tokenTextSplitter;

    //Write to db (Load)
    private VectorStore vectorStore;

    public ETLHelperImpl(TokenTextSplitter tokenTextSplitter, VectorStore vectorStore) {
        this.tokenTextSplitter = tokenTextSplitter;
        this.vectorStore = vectorStore;
    }

    @Override
    public ResponseMessageDTO loadEmbeddingDataIntoVectorStore(List<String> stringList) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        try {
            if (!stringList.isEmpty()) {
                List<Document> documentList = new ArrayList<>();
                stringList.forEach(string -> documentList.add(new Document(string)));
                return addEmbeddingDataToTheVectorDatabase(documentList);
            }
            else{
                responseMessageDTO.setMessage("List is empty.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseMessageDTO;
    }

    @Override
    public ResponseMessageDTO loadEmbeddingDataIntoVectorStore(Resource resource) {
        TikaDocumentReader tikaDocumentReader = TikaDocumentReaderConfigHelper.getTikaDocumentReaderInstance(resource);
        List<Document> documentList = tikaDocumentReader.get();
        log.info("documentList size: " + documentList.size());
        for (Document document : documentList) {
            log.info("Loading embedding data into vector store"+document.getText());
            Map<String, Object> map = document.getMetadata();
            for(Map.Entry<String, Object> entry : map.entrySet()){
                log.info("key: "+entry.getKey()+" value: "+entry.getValue());
            }
        }
        return addEmbeddingDataToTheVectorDatabase(documentList);
    }

    private ResponseMessageDTO addEmbeddingDataToTheVectorDatabase(List<Document> documentList){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        try {
            if (documentList!=null && !documentList.isEmpty()){
                List<Document> splittedTokenDocumentList = tokenTextSplitter.apply(documentList);
                vectorStore.add(splittedTokenDocumentList);
                responseMessageDTO.setMessage("Data added to the DB.");
            }
            else{
                responseMessageDTO.setMessage("Empty document passed/ Nothing to embed and add to  the DB.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseMessageDTO;
    }
}
