package com.example.springaichatmodel.API;


import com.example.springaichatmodel.DTO.ChatPromptDTO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/v1")
public interface ChatGPTModelAPI {

    //API to get the response from AImodel as a String.
    @PostMapping(value = "/getResponseAsString")
    ResponseEntity<String> getResponseAsString(@RequestBody ChatPromptDTO chatPromptDTO);

    //API to get the response from AIModel as an Entity.
    @PostMapping(value = "/getResponseAsEntity")
    ResponseEntity<ChatPromptDTO> getResponseAsEntity(@RequestBody ChatPromptDTO chatPromptDTO);

    //API to get the response from AIModel as Generics.
    @PostMapping(value = "/getResponseAsGenerics")
    ResponseEntity<List<ChatPromptDTO>> getResponseAsGenerics(@RequestBody ChatPromptDTO chatPromptDTO);

    @GetMapping(value = "/getContentsInMemory")
    ResponseEntity<List<Message>> getContentsInMemory();

}
