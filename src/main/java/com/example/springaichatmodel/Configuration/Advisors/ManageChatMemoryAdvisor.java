package com.example.springaichatmodel.Configuration.Advisors;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ManageChatMemoryAdvisor {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory
                .builder()
                .maxMessages(10)
                .build();
    }
}
