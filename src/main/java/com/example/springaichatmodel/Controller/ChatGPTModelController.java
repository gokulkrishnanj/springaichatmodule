package com.example.springaichatmodel.Controller;


import com.example.springaichatmodel.API.ChatGPTModelAPI;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.image.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatGPTModelController implements ChatGPTModelAPI {

    private ChatGPTModelService chatGPTModelService;

    public ChatGPTModelController(ChatGPTModelService chatGPTModelService) {
        this.chatGPTModelService = chatGPTModelService;
    }

    public ResponseEntity<String> getResponseAsString(ChatPromptDTO chatPromptDTO) {
        return new ResponseEntity<>(chatGPTModelService.getResponseAsString(chatPromptDTO.getQuestion()), HttpStatus.OK);
    }

    public ResponseEntity<ChatPromptDTO> getResponseAsEntity(ChatPromptDTO chatPromptDTO) {
        return new ResponseEntity<>(chatGPTModelService.getResponseAsEntity(chatPromptDTO.getQuestion()), HttpStatus.OK);
    }

    public ResponseEntity<List<ChatPromptDTO>> getResponseAsGenerics(ChatPromptDTO chatPromptDTO) {
        return new ResponseEntity<>(chatGPTModelService.getResponseAsGenerics(chatPromptDTO.getQuestion()), HttpStatus.OK);
    }

    public ResponseEntity<List<Message>> getContentsInMemory() {
        return new ResponseEntity<>(chatGPTModelService.getContentsInMemory(), HttpStatus.OK);
    }

    public ResponseEntity<List<String>> generateImageFromInstruction(String instruction){
        return new ResponseEntity<>(chatGPTModelService.generateImageFromInstruction(instruction), HttpStatus.OK);
    }
}
