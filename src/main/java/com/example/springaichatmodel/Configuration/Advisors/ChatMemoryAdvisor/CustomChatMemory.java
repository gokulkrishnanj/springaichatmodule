package com.example.springaichatmodel.Configuration.Advisors.ChatMemoryAdvisor;

import com.example.springaichatmodel.Document.ChatMemoryDocument;
import com.example.springaichatmodel.Repository.UserChatMemoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomChatMemory implements ChatMemory {

    public static final int DEFAULT_MAX_MESSAGE= 10;
    private static final Logger log = LogManager.getLogger(CustomChatMemory.class);
    private final int maxMessage;
    private String userId = "123421";

    private UserChatMemoryRepository userChatMemoryRepository;

    public CustomChatMemory(UserChatMemoryRepository userChatMemoryRepository,int maxMessage) {
        this.userChatMemoryRepository = userChatMemoryRepository;
        this.maxMessage = maxMessage;
    }

    public CustomChatMemory(Builder builder) {
        this.userChatMemoryRepository = builder.userChatMemoryRepository;
        this.maxMessage = builder.maxMessage;
    }


    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ChatMemoryDocument> chatMemoryDocumentList = userChatMemoryRepository.findByUserIdAndConversationId(userId, conversationId);
        List<Message> memoryDocumentList = chatMemoryDocumentList.get(0).getChatMessageList();
        List<Message> processedMessage = process(messages,memoryDocumentList);
        chatMemoryDocumentList.get(0).setChatMessageList(processedMessage);
        ChatMemoryDocument chatMemoryDocument = chatMemoryDocumentList.get(0);
        userChatMemoryRepository.save(chatMemoryDocument);
    }

    @Override
    public List<Message> get(String conversationId) {
        log.info("conversationId::::"+conversationId);
        List<ChatMemoryDocument> chatMemoryDocumentList = userChatMemoryRepository.findByUserIdAndConversationId(userId, conversationId);
        return chatMemoryDocumentList.get(0).getChatMessageList();
    }

    @Override
    public void clear(String conversationId) {
        userChatMemoryRepository.deleteChatMemoryDocumentByUserIdAndConversationId(userId,conversationId);
    }

    private List<Message> process(List<Message> memoryMessages, List<Message> newMessages) {
        List<Message> processedMessages = new ArrayList<>();

        Set<Message> memoryMessagesSet = new HashSet<>(memoryMessages);
        boolean hasNewSystemMessage = newMessages.stream()
                .filter(SystemMessage.class::isInstance)
                .anyMatch(message -> !memoryMessagesSet.contains(message));

        memoryMessages.stream()
                .filter(message -> !(hasNewSystemMessage && message instanceof SystemMessage))
                .forEach(processedMessages::add);

        processedMessages.addAll(newMessages);

        if (processedMessages.size() <= this.maxMessage) {
            return processedMessages;
        }

        int messagesToRemove = processedMessages.size() - this.maxMessage;

        List<Message> trimmedMessages = new ArrayList<>();
        int removed = 0;
        for (Message message : processedMessages) {
            if (message instanceof SystemMessage || removed >= messagesToRemove) {
                trimmedMessages.add(message);
            }
            else {
                removed++;
            }
        }

        return trimmedMessages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserChatMemoryRepository userChatMemoryRepository;
        private int maxMessage = DEFAULT_MAX_MESSAGE;


        public Builder(){}

//        public Builder(UserChatMemoryRepository userChatMemoryRepository,int maxMessage) {
//            this.userChatMemoryRepository=userChatMemoryRepository;
//            this.maxMessage = maxMessage;
//        }

        public Builder setMaxMessage(int maxMessage) {
            this.maxMessage = maxMessage;
            return this;
        }

        public  Builder setUserChatMemoryRepository(UserChatMemoryRepository userChatMemoryRepository) {
            this.userChatMemoryRepository = userChatMemoryRepository;
            return this;
        }

        public CustomChatMemory build() {
            return new CustomChatMemory(this);
        }
    }

}
