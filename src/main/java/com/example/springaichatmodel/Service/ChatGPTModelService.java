package com.example.springaichatmodel.Service;

import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChatGPTModelService {
    String getResponseAsString(String message);

    ChatPromptDTO getResponseAsEntity(String message);

    List<ChatPromptDTO> getResponseAsGenerics(String message);

    List<Message> getContentsInMemory();

    List<String> generateImageFromInstruction(String instruction);

    List<ImageDetailsDTO> getResponseByAnalysingTheMedia(MultipartFile file, String instructions) throws IOException;

    ResponseMessageDTO clearChatMemory();

    List<ChatPromptDTO> newChat(String message);

    List<String> getAllConversationIds();
}
