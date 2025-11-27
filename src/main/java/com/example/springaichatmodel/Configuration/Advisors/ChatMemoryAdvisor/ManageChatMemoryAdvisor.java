package com.example.springaichatmodel.Configuration.Advisors.ChatMemoryAdvisor;

import com.example.springaichatmodel.Repository.UserChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ManageChatMemoryAdvisor {

    @Autowired
    private UserChatMemoryRepository userChatMemoryRepository;

    @Bean
    @Lazy
    public ChatMemory chatMemory() {
        return CustomChatMemory
                .builder()
                .setMaxMessage(10)
                .setUserChatMemoryRepository(userChatMemoryRepository)
                .build();
    }

//    @Bean
//    @Lazy
//    public ChatMemory chatMemory() {
//        return new CustomChatMemory
//                .Builder()
//                .setMaxMessage(10)
//                .setUserChatMemoryRepository(userChatMemoryRepository)
//                .build();
//    }

}
