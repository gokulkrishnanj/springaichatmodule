package com.example.springaichatmodel.Configuration;

import com.example.springaichatmodel.Utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Component
public class CreatePromptForChat {

    // Prompt with user message(USER) and System message.
    public Prompt createPromptForRequest(String message) {
        Message userMessage = new UserMessage(message);
        String systemText = Constants.systemDefaultPromptMessage;
        Message systemMessage = new SystemPromptTemplate(systemText).createMessage();
        return new Prompt(List.of(userMessage, systemMessage));
    }

    public Prompt createPromptForDataExtractionFromImage(MultipartFile file, String message) {
        UserMessage userMessage = new UserMessage(message);
        log.info("debug message:" + message);
        UserMessage userMessageImage = new UserMessage(file.getResource());
        Message systemMessageForSafetyPurpose = new SystemPromptTemplate(Constants.defaultSystemPromptMessageForImageSafetyPupose).createMessage();
        Message systemMessageForImageOptimization = new SystemPromptTemplate(Constants.defaultSystemPromptMessageForImageOptimization).createMessage();
        return new Prompt(List.of(userMessageImage, userMessage, systemMessageForSafetyPurpose, systemMessageForImageOptimization));
    }
}