package com.example.springaichatmodel.Service;

import com.example.springaichatmodel.DTO.ChatPromptDTO;

public interface ChatGPTModelService {
    ChatPromptDTO getResponse(String message);
}
