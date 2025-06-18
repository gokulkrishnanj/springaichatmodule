package com.example.springaichatmodel.API;


import com.example.springaichatmodel.DTO.ChatPromptDTO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.image.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //API to get contents in the chat memory.
    @GetMapping(value = "/getContentsInMemory")
    ResponseEntity<List<Message>> getContentsInMemory();

    @PostMapping(value = "/generateImage")
    ResponseEntity<List<String>> generateImageFromInstruction(@RequestParam(value = "instruction")String instruction);
}
