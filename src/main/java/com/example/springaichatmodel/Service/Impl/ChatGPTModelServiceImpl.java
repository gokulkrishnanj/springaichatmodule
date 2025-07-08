package com.example.springaichatmodel.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.springaichatmodel.Configuration.CreatePromptForChat;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.*;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {


    private ChatClient chatClient;
    private CreatePromptForChat createPromptForChat;
    private ChatMemory chatMemory;
    private StabilityAiImageModel stabilityAiImageModel;
    private Cloudinary cloudinary;

    ChatGPTModelServiceImpl(ChatClient chatClient,
                            CreatePromptForChat createPromptForChat,
                            ChatMemory chatMemory,
                            StabilityAiImageModel stabilityAiImageModel,
                            Cloudinary cloudinary) {
        this.chatClient = chatClient;
        this.createPromptForChat = createPromptForChat;
        this.chatMemory = chatMemory;
        this.stabilityAiImageModel = stabilityAiImageModel;
        this.cloudinary = cloudinary;
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

    //need to handle the url issue.
    @Override
    public List<String> generateImageFromInstruction(String instructions) {
        System.out.println("Instruction:" + instructions);
        ImagePrompt imagePrompt = new ImagePrompt(instructions,
                StabilityAiImageOptions.builder().N(2).stylePreset("cinematic").height(1024).width(1024).build());
        ImageResponse imageResponse = stabilityAiImageModel.call(imagePrompt);
        List<String> imageURLList = new ArrayList<>();
        imageResponse.getResults().forEach(result -> {
            String url = null;
            try {
                url = getURLFromBase64(result.getOutput().getB64Json());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageURLList.add(url);
        });
        return imageURLList;
    }

    private String getURLFromBase64(String base64Image) throws IOException {
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];  // Remove "data:image/png;base64," part
        }
        String dataUri = "data:image/png;base64," + base64Image;
        Map<String, Object> uploadResult = cloudinary.uploader().upload(dataUri, ObjectUtils.asMap("resource_type", "image"));
        System.out.println("map:" + uploadResult);
        return uploadResult.get("url").toString();
    }

    public List<ImageDetailsDTO> getResponseByAnalysingTheMedia(MultipartFile file, String instructions) throws IOException {
        Prompt prompt = createPromptForChat.createPromptForDataExtractionFromImage(file, instructions);
        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, Constants.defaultConversationId))
                .call()
                .entity(new ParameterizedTypeReference<List<ImageDetailsDTO>>() {
                });
    }

}
