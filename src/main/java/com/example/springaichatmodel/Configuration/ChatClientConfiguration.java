package com.example.springaichatmodel.Configuration;


import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseURL;

    @Value("${spring.ai.openai.chat.completions-path}")
    private String completionsPath;

    @Autowired
    private ChatMemory chatMemory;

//    @Bean
//    public ChatClient customChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
//        return chatClientBuilder.defaultAdvisors(new SafeGuardAdvisor(Constants.restrictedWordsList),
//                        new SimpleLoggerAdvisor(),
//                        MessageChatMemoryAdvisor.builder(chatMemory).build())
//                .build();
//    }

    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi
                .builder()
                .baseUrl(baseURL)
                .completionsPath(completionsPath)
                .apiKey(apiKey)
                .build();
    }

    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
        return OpenAiChatModel
                .builder()
                .openAiApi(openAiApi)
                .build();
    }

    @Bean
    public ChatClient customOpenAIChatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient
                .builder(openAiChatModel).
                defaultAdvisors(new SafeGuardAdvisor(Constants.restrictedWordsList),
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}
