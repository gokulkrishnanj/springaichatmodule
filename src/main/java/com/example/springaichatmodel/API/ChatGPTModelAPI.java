package com.example.springaichatmodel.API;


import com.example.springaichatmodel.DTO.ChatPromptDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/v1")
public interface ChatGPTModelAPI {

    @PostMapping(value = "/getResponse")
    ResponseEntity<ChatPromptDTO> getResponse(@RequestBody ChatPromptDTO chatPromptDTO);
}
