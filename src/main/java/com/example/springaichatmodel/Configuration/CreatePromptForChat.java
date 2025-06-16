package com.example.springaichatmodel.Configuration;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CreatePromptForChat {

    // Prompt with user message(USER) and System message.
    public Prompt createPromptForRequest(String message){
        Message userMessage = new UserMessage(message);
        String systemText = "If the request is empty consider user asking who are you and respond who you are and what are you capable of. If the request is like statement just listen and respond. If the request contains any grammar or spelling mistakes add the correct request in the searchInstead field.";
        Message systemMessage = new SystemPromptTemplate(systemText).createMessage();
        return new Prompt(List.of(userMessage, systemMessage));
    }
}