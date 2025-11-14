package com.example.springaichatmodel.ETL.Service.Impl;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.ETL.Helper.ETLHelper;
import com.example.springaichatmodel.ETL.Service.ETLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    }

    @Override
    public ResponseMessageDTO extractEmbeddingDataFromDocument(MultipartFile file) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        Resource resource = file.getResource();
        log.info("resourceName:"+resource.getFilename());
        return ETLhelper.loadEmbeddingDataIntoVectorStore(resource);
    }
}