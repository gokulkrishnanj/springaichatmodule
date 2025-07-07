package com.example.springaichatmodel.Service;

import com.example.springaichatmodel.DTO.ChatPromptDTO;
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

    String getResponseByAnalysingTheImage(MultipartFile file, String instructions) throws IOException;
}
