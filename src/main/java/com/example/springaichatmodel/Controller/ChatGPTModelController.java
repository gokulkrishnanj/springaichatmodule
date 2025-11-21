package com.example.springaichatmodel.Controller;


import com.example.springaichatmodel.API.ChatGPTModelAPI;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public ResponseEntity<List<String>> generateImageFromInstruction(String instruction) {
        return new ResponseEntity<>(chatGPTModelService.generateImageFromInstruction(instruction), HttpStatus.OK);
    }

    public ResponseEntity<List<ImageDetailsDTO>> getResponseByAnalysingTheMedia(MultipartFile file, String instructions) throws IOException {
        return new ResponseEntity<>(chatGPTModelService.getResponseByAnalysingTheMedia(file, instructions), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> clearChatMemory() {
        return new ResponseEntity<>(chatGPTModelService.clearChatMemory(), HttpStatus.OK);
    }

    public ResponseEntity<List<ChatPromptDTO>> newChat(ChatPromptDTO chatPromptDTO){
        return new ResponseEntity<>(chatGPTModelService.newChat(chatPromptDTO.getQuestion()), HttpStatus.OK);
    }
}
