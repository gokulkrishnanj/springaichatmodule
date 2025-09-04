package com.example.springaichatmodel.ETL.Helper;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ETLHelper {

    public ResponseMessageDTO loadEmbeddingDataIntoVectorStore(List<String> list);

    public ResponseMessageDTO loadEmbeddingDataIntoVectorStore(Resource resource);

}
