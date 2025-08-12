package com.example.springaichatmodel.ETL.Controller;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.ETL.API.ETLAPIs;
import com.example.springaichatmodel.ETL.Service.ETLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ETLController implements ETLAPIs {

    private ETLService etlService;

    ETLController(ETLService etlService) {
        this.etlService = etlService;
    }

    public ResponseEntity<ResponseMessageDTO> extractDataFromString(List<String> stringList){
        return new ResponseEntity<>(etlService.extractDataFromString(stringList), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> extractDataFromDocument(MultipartFile file) {
        return new ResponseEntity<>(etlService.extractDataFromDocument(file), HttpStatus.OK);
    }

}
