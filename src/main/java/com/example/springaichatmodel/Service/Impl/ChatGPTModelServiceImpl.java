package com.example.springaichatmodel.Service.Impl;

import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {

    private ChatClient chatClient;

    public ChatGPTModelServiceImpl(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
    }

    @Override
    public ChatPromptDTO getResponse(String message){
        return chatClient
                .prompt(message)
                .call()
                .entity(ChatPromptDTO.class);
    }
}
