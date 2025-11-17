package com.example.springaichatmodel.Configuration;


import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ChatClientConfiguration {

    @Autowired
    private ChatMemory chatMemory;

    @Bean
    @Lazy
    public ChatClient.Builder chatClientBuilder(OpenAiChatModel geminiModel) {
        return ChatClient.builder(geminiModel);
    }

    @Bean(value = "geminiAIChatClient")
    @Lazy
    @ConditionalOnProperty(name = "spring.ai.openai.api-key")
    public ChatClient customChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(new SafeGuardAdvisor(Constants.restrictedWordsList),
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build())
                .build();
    }

}
