package com.example.springaichatmodel.ETL.API;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value = "${api/v1/ETL}")
public interface ETLAPIs {

    @PostMapping(value = "${extractDataFromString}")
    public ResponseEntity<ResponseMessageDTO> extractDataFromString(@RequestBody List<String> stringList);

    @PostMapping(value = "${extractDataFromDoc}")
    public ResponseEntity<ResponseMessageDTO> extractDataFromDocument(@RequestParam(value = "document") MultipartFile file);

}
