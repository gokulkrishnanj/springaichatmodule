package com.example.springaichatmodel.Configuration;

import com.example.springaichatmodel.Utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class CreatePromptForChat {

    // Prompt with user message(USER) and System message.
    public Prompt createPromptForRequest(String message) {
        Message userMessage = new UserMessage(message);
        String systemText = Constants.systemDefaultPromptMessage;
        Message systemMessage = new SystemPromptTemplate(systemText).createMessage();
        Message safeGuardDefaultSystemMessage = new SystemPromptTemplate(Constants.defaultSafeGuardSystemPromptMessage).createMessage();
        return new Prompt(List.of(userMessage, systemMessage, safeGuardDefaultSystemMessage));
    }

    public Prompt createPromptForDataExtractionFromImage(MultipartFile file, String message) {
        log.info("contentType:" + file.getContentType());
        log.info("message:" + message);
        MimeType mimeType = MediaType.valueOf(file.getContentType());
        UserMessage userMessage = UserMessage.builder()
                .text(message)
                .media(new Media(mimeType, file.getResource()))
                .metadata(new HashMap<>())
                .build();
        Message systemMessageForSafetyPurpose = new SystemPromptTemplate(Constants.defaultSystemPromptMessageForImageSafetyPurpose).createMessage();
        Message systemMessageForImageOptimization = new SystemPromptTemplate(Constants.defaultSystemPromptMessageForImageOptimization).createMessage();
        Message safeGuardDefaultSystemMessage = new SystemPromptTemplate(Constants.defaultSafeGuardSystemPromptMessage).createMessage();
        return new Prompt(List.of(userMessage, systemMessageForSafetyPurpose, systemMessageForImageOptimization, safeGuardDefaultSystemMessage));
    }
}