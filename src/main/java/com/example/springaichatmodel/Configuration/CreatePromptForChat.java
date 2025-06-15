package com.example.springaichatmodel.Configuration;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreatePromptForChat {

    public Prompt createPrompt(String... message) {
        PromptTemplate promptTemplate = new PromptTemplate("This {Request} may contains spelling mistakes and grammatical mistakes show correct question in searchInstead field, please ignore and process.");
        Prompt prompt = promptTemplate.create(Map.of("Request", message));
        return prompt;
    }

    public Prompt systemMessagePrompt(){
        PromptTemplate promptTemplate = new PromptTemplate("Greet them using any rando quote and request how you can help them?");
        Prompt prompt = promptTemplate.create();
        return  prompt;
    }
}