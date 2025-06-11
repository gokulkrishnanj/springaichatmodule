package com.example.springaichatmodel.Configuration;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient customChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
}
