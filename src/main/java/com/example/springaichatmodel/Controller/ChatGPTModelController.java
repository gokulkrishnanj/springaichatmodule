package com.example.springaichatmodel.Controller;


import com.example.springaichatmodel.API.ChatGPTModelAPI;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatGPTModelController implements ChatGPTModelAPI {

    private ChatGPTModelService chatGPTModelService;

    public ChatGPTModelController(ChatGPTModelService chatGPTModelService){
        this.chatGPTModelService = chatGPTModelService;
    }

    public ResponseEntity<ChatPromptDTO> getResponse(ChatPromptDTO chatPromptDTO){
        ChatPromptDTO chatPromptDTOResponse= new ChatPromptDTO();
//        chatPromptDTOResponse.setMessage(chatGPTModelService.getResponse(chatGPTModelService.getResponse(chatPromptDTO.getMessage())));
        return new ResponseEntity<>(chatGPTModelService.getResponse(chatPromptDTO.getQuestion()),HttpStatus.OK);
    }
}
