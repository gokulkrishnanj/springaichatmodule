package com.example.springaichatmodel.ETL.Service;


import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ETLService {

    public ResponseMessageDTO extractDataFromString(List<String> stringList);

    public ResponseMessageDTO extractDataFromDocument(MultipartFile file);
}
