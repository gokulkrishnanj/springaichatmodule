package com.example.springaichatmodel.Configuration.Advisors.ChatMemoryAdvisor;

import com.example.springaichatmodel.Document.ChatMemoryDocument;
import com.example.springaichatmodel.Model.ChatMessage;
import com.example.springaichatmodel.Repository.UserChatMemoryRepository;
import com.example.springaichatmodel.Utils.Helper.BuildMessageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.*;

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
    public void add(String conversationId, List<Message> newMessages) {
        log.info("inside the add method and conversationId:"+conversationId);
//        newMessages.forEach(message -> {
//            log.info("adding a message:"+message.getMessageType().getValue()+"text:"+message.getText());
//        });
        ChatMemoryDocument chatMemoryDocument = userChatMemoryRepository.findByUserIdAndConversationId(userId, conversationId);
        if(chatMemoryDocument==null){
            List<ChatMessage> chatMessages = new ArrayList<>();
            chatMessages=process(new ArrayList<>(),newMessages);
            ChatMemoryDocument newChatMemoryDocument = new ChatMemoryDocument();
            newChatMemoryDocument.setUserId(userId);
            newChatMemoryDocument.setConversationId(conversationId);
            newChatMemoryDocument.setChatMessages(chatMessages);
            userChatMemoryRepository.save(newChatMemoryDocument);
            return;
        }
        List<ChatMessage> chatMessageList = chatMemoryDocument.getChatMessages();
        List<Message> oldMessages = chatMessageList.stream()
                .map(chatMessage -> {
                    BuildMessageHelper helper = new BuildMessageHelper(chatMessage.getRole(),chatMessage.getContent(),chatMessage.getMetadata());
                    return helper.buildMessage();
                }).toList();
        List<ChatMessage> processedMessages = process(oldMessages, newMessages);
        chatMessageList=processedMessages;
        chatMemoryDocument.setChatMessages(chatMessageList);
        userChatMemoryRepository.save(chatMemoryDocument);
    }

    @Override
    public List<Message> get(String conversationId) {
        log.info("inside the get method and conversationId"+conversationId);
        ChatMemoryDocument chatMemoryDocument = userChatMemoryRepository.findByUserIdAndConversationId(userId, conversationId);
        if(chatMemoryDocument==null){
            return new ArrayList<Message>();
        }
        List<ChatMessage> chatMessageList = chatMemoryDocument.getChatMessages();
        return chatMessageList
                .stream()
                .map(chatMessage -> {
                    BuildMessageHelper buildMessageHelper = new BuildMessageHelper(chatMessage.getRole(),chatMessage.getContent(),chatMessage.getMetadata());
                    return buildMessageHelper.buildMessage();

                }).toList();
    }

    @Override
    public void clear(String conversationId) {
        userChatMemoryRepository.deleteChatMemoryDocumentByUserIdAndConversationId(userId,conversationId);
    }

    private List<ChatMessage> process(List<Message> oldMessages, List<Message> newMessages) {
        List<ChatMessage> processedMessages = new ArrayList<>();
        Set<Message> oldMemoryMessagesSet = new HashSet<>(oldMessages);
        boolean hasNewSystemMessage = newMessages.stream()
                .filter(SystemMessage.class::isInstance)
                .anyMatch(message -> !oldMemoryMessagesSet.contains(message));

        oldMessages.stream()
                .filter(message -> !(hasNewSystemMessage && message instanceof SystemMessage))
                .forEach((message -> {
                    ChatMessage chatMessage = new ChatMessage(message);
                    processedMessages.add(chatMessage);
                }));
        newMessages.stream()
                        .forEach(message -> {
                            ChatMessage chatMessage = new ChatMessage(message);
                            processedMessages.add(chatMessage);
                        });

        if (processedMessages.size() <= this.maxMessage) {
            return processedMessages;
        }

        int messagesToRemove = processedMessages.size() - this.maxMessage;

        List<ChatMessage> trimmedMessages = new ArrayList<>();
        int removed = 0;
        for (ChatMessage message : processedMessages) {
            if (message.getRole().equals(MessageType.SYSTEM) || removed >= messagesToRemove) {
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
