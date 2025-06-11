package com.example.springaichatmodel.Service.Impl;

import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {


    private ChatClient chatClient;

    ChatGPTModelServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getResponseAsString(String message) {
        return chatClient
                .prompt(message)
                .call()
                .content();
    }

    @Override
    public ChatPromptDTO getResponseAsEntity(String message) {
        return chatClient
                .prompt(message)
                .call()
                .entity(ChatPromptDTO.class);
    }

    @Override
    public List<ChatPromptDTO> getResponseAsGenerics(String message) {
        return chatClient
                .prompt(message)
                .call()
                .entity(new ParameterizedTypeReference<List<ChatPromptDTO>>() {
                });
    }
}
