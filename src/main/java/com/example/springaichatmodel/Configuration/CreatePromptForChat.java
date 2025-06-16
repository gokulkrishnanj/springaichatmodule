package com.example.springaichatmodel.Configuration;

import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePromptForChat {

    // Prompt with user message(USER) and System message.
    public Prompt createPromptForRequest(String message) {
        Message userMessage = new UserMessage(message);
        String systemText = Constants.systemDefaultPromptMessage;
        Message systemMessage = new SystemPromptTemplate(systemText).createMessage();
        return new Prompt(List.of(userMessage, systemMessage));
    }
}