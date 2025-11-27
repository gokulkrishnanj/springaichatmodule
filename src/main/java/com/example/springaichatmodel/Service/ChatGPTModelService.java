package com.example.springaichatmodel.Service;

import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChatGPTModelService {
    String getResponseAsString(String message,  String conversationId);

    ChatPromptDTO getResponseAsEntity(String message,  String conversationId);

    List<ChatPromptDTO> getResponseAsGenerics(String message,  String conversationId);

    List<Message> getContentsInMemory(String conversationId);

    List<String> generateImageFromInstruction(String instruction);

    List<ImageDetailsDTO> getResponseByAnalysingTheMedia(MultipartFile file, String instructions) throws IOException;

    ResponseMessageDTO clearChatMemory(String conversationId);

    List<ChatPromptDTO> newChat(String message);

    List<String> getAllConversationIds();
}
