package com.example.springaichatmodel.Service.Impl;

import com.example.springaichatmodel.Configuration.CreatePromptForChat;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {


    private ChatClient chatClient;
    private CreatePromptForChat createPromptForChat;

    ChatGPTModelServiceImpl(ChatClient chatClient, CreatePromptForChat createPromptForChat) {
        this.chatClient = chatClient;
        this.createPromptForChat = createPromptForChat;
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
        Prompt prompt = createPromptForChat.createPrompt(message);
        return chatClient
                .prompt(prompt)
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
