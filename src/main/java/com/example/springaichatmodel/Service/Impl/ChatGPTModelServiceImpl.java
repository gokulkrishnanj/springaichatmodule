package com.example.springaichatmodel.Service.Impl;

import com.example.springaichatmodel.Configuration.CreatePromptForChat;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {


    private ChatClient chatClient;
    private CreatePromptForChat createPromptForChat;
    private ChatMemory chatMemory;

    ChatGPTModelServiceImpl(ChatClient chatClient, CreatePromptForChat createPromptForChat, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.createPromptForChat = createPromptForChat;
        this.chatMemory = chatMemory;
    }

    @Override
    public String getResponseAsString(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message);
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    @Override
    public ChatPromptDTO getResponseAsEntity(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message);
        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, Constants.defaultConversationId))
                .call()
                .entity(ChatPromptDTO.class);
    }

    @Override
    public List<ChatPromptDTO> getResponseAsGenerics(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message);
        return chatClient
                .prompt(prompt)
                .call()
                .entity(new ParameterizedTypeReference<List<ChatPromptDTO>>() {
                });
    }

    @Override
    public List<Message> getContentsInMemory() {
        return chatMemory.get(Constants.defaultConversationId);
    }

}
