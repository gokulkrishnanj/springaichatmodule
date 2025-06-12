package com.example.springaichatmodel.Configuration;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.ModelRequest;
import org.springframework.ai.model.ModelResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CreatePromptForChat{

    public Prompt createPrompt(String ... message){
        List<String> messageList = List.of(message);
        PromptTemplate promptTemplate = new PromptTemplate("Add some reference related to this {Question}");
        Prompt prompt = promptTemplate.create(Map.of("Question",messageList));
        return prompt;
    }
}