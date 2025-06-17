package com.example.springaichatmodel.Service;

import com.example.springaichatmodel.DTO.ChatPromptDTO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.image.Image;

import java.util.List;

public interface ChatGPTModelService {
    String getResponseAsString(String message);

    ChatPromptDTO getResponseAsEntity(String message);

    List<ChatPromptDTO> getResponseAsGenerics(String message);

    List<Message> getContentsInMemory();

    List<Image> generateImageFromInstruction(String instruction);
}
