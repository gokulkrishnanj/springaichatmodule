package com.example.springaichatmodel.Controller;

import com.example.springaichatmodel.API.VectorStoreAPI;
import com.example.springaichatmodel.Service.VectorStoreAPIService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VectorStoreAPIController implements VectorStoreAPI {

    private VectorStoreAPIService vectorStoreAPIService;

    VectorStoreAPIController(VectorStoreAPIService vectorStoreAPIService) {
        this.vectorStoreAPIService = vectorStoreAPIService;
    }

    public void addDocumentsToVector(List<String> documentsList) {
        vectorStoreAPIService.addDocumentsToVector(documentsList);
    }
}
