package com.example.springaichatmodel.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "${api/v1}")
public interface VectorStoreAPI {
    @PostMapping(value = "${addDocumentsToVector}")
    public void addDocumentsToVector(@RequestBody List<String> documentsList);
}
