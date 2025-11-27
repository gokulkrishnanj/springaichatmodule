package com.example.springaichatmodel.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.springaichatmodel.Configuration.CreatePromptForChat;
import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.Document.ChatMemoryDocument;
import com.example.springaichatmodel.Repository.UserChatMemoryRepository;
import com.example.springaichatmodel.Service.ChatGPTModelService;
import com.example.springaichatmodel.Utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.*;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.springaichatmodel.Utils.Constants.userId;

@Service
public class ChatGPTModelServiceImpl implements ChatGPTModelService {


    private static final Logger log = LogManager.getLogger(ChatGPTModelServiceImpl.class);
    @Qualifier("geminiAIChatClient")
    private ChatClient chatClient;
    private CreatePromptForChat createPromptForChat;
    private ChatMemory chatMemory;
    private StabilityAiImageModel stabilityAiImageModel;
    private Cloudinary cloudinary;
    private UserChatMemoryRepository userChatMemoryRepository;

    ChatGPTModelServiceImpl(ChatClient chatClient,
                            CreatePromptForChat createPromptForChat,
                            ChatMemory chatMemory,
                            StabilityAiImageModel stabilityAiImageModel,
                            Cloudinary cloudinary,
                            UserChatMemoryRepository userChatMemoryRepository) {
        this.chatClient = chatClient;
        this.createPromptForChat = createPromptForChat;
        this.chatMemory = chatMemory;
        this.stabilityAiImageModel = stabilityAiImageModel;
        this.cloudinary = cloudinary;
        this.userChatMemoryRepository = userChatMemoryRepository;
    }

    @Override
    public String getResponseAsString(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message,false);
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    @Override
    public ChatPromptDTO getResponseAsEntity(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message,false);
        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, Constants.defaultConversationId))
                .call()
                .entity(ChatPromptDTO.class);
    }

    @Override
    public List<ChatPromptDTO> getResponseAsGenerics(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message,false);
        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .entity(new ParameterizedTypeReference<List<ChatPromptDTO>>() {
                });
    }

    @Override
    public List<ChatPromptDTO> newChat(String message) {
        Prompt prompt = createPromptForChat.createPromptForRequest(message, true);
        String conversationId = UUID.randomUUID().toString();
        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID,conversationId))
                .call()
                .entity(new ParameterizedTypeReference<List<ChatPromptDTO>>() {
                });
    }

    @Override
    public List<Message> getContentsInMemory() {
        return chatMemory.get(userId);
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

    @Override
    public List<ImageDetailsDTO> getResponseByAnalysingTheMedia(MultipartFile file, String instructions) throws IOException {
        Prompt prompt = createPromptForChat.createPromptForDataExtractionFromImage(file, instructions);
        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, Constants.defaultConversationId))
                .call()
                .entity(new ParameterizedTypeReference<List<ImageDetailsDTO>>() {
                });
    }

    @Override
    public ResponseMessageDTO clearChatMemory() {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        chatMemory.clear(userId);
        if (chatMemory.get(userId).size() == 0) {
            responseMessageDTO.setMessage("Memory cleared");
            return responseMessageDTO;
        }
        responseMessageDTO.setMessage("Try again Data of size " + chatMemory.get(userId).size() + " is present");
        return responseMessageDTO;
    }

    @Override
    public List<String> getAllConversationIds() {
        List<String>  conversationIdList = new ArrayList<>();
        List<ChatMemoryDocument> chatMemoryDocumentList = userChatMemoryRepository.findByUserId(userId);
        if(chatMemoryDocumentList!=null && !chatMemoryDocumentList.isEmpty()){
            log.info("not null");
            conversationIdList = chatMemoryDocumentList.stream()
                    .map(chatMemoryDocument -> {
                        return chatMemoryDocument.getConversationId();
                    })
                    .toList();
        }
        return conversationIdList;
    }
}
