package com.example.springaichatmodel.ETL.API;

import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value = "${api/v1/ETL}")
public interface ETLAPIs {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "${extractEmbeddingDataFromString}")
    public ResponseEntity<ResponseMessageDTO> extractEmbeddingDataFromString(@RequestBody List<String> stringList);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "${extractEmbeddingDataFromDocument}")
    public ResponseEntity<ResponseMessageDTO> extractEmbeddingDataFromDocument(@RequestParam(value = "document") MultipartFile file);

}
