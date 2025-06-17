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
import org.springframework.ai.image.*;
import org.springframework.ai.model.stabilityai.autoconfigure.StabilityAiImageAutoConfiguration;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {


    private ChatClient chatClient;
    private CreatePromptForChat createPromptForChat;
    private ChatMemory chatMemory;
    private StabilityAiImageModel stabilityAiImageModel;

    ChatGPTModelServiceImpl(ChatClient chatClient, CreatePromptForChat createPromptForChat, ChatMemory chatMemory, StabilityAiImageModel stabilityAiImageModel) {
        this.chatClient = chatClient;
        this.createPromptForChat = createPromptForChat;
        this.chatMemory = chatMemory;
        this.stabilityAiImageModel = stabilityAiImageModel;
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
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, Constants.defaultConversationId))
                .call()
                .entity(new ParameterizedTypeReference<List<ChatPromptDTO>>() {
                });
    }

    @Override
    public List<Message> getContentsInMemory() {
        return chatMemory.get(Constants.defaultConversationId);
    }

    @Override
    public List<Image> generateImageFromInstruction(String instructions){
        ImagePrompt imagePrompt = new ImagePrompt(instructions,
                StabilityAiImageOptions.builder().N(2).stylePreset("cinematic").height(1024).width(1024).build());
        ImageResponse imageResponse = stabilityAiImageModel.call(imagePrompt);
        List<Image> imageURLList = new ArrayList<>();
        imageResponse.getResults().forEach(result->{
            System.out.println("url:"+result.getOutput());
            imageURLList.add(result.getOutput());
        });
        return imageURLList;
    }

}
