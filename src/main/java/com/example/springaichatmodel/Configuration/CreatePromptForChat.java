package com.example.springaichatmodel.Configuration;

import com.example.springaichatmodel.ETL.Service.ETLService;
import com.example.springaichatmodel.Utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

import static com.example.springaichatmodel.Utils.Constants.userId;

@Slf4j
@Component
public class CreatePromptForChat {

    private VectorStore vectorStore;

    private ETLService etlService;

    private ChatMemory chatMemory;

    public CreatePromptForChat(VectorStore vectorStore, ETLService etlService, ChatMemory chatMemory) {
        this.vectorStore = vectorStore;
        this.etlService = etlService;
        this.chatMemory = chatMemory;
    }

    // Prompt with user message(USER) and System message.
    public Prompt createPromptForRequest(String message, boolean isNewChat) {
        StringBuilder messageStringBuilder = new StringBuilder(message);
        // checking whether the chat is newChat or old chat (if old it will look in the chatmemory and get the similarity)
        if(!isNewChat){
//            StringBuilder chatMemoryStringBuilder = new StringBuilder();
//            List<Message> messageList = chatMemory.get(userId);
//            messageList.forEach(messageData -> {
//                chatMemoryStringBuilder.append(messageData.getText());
//            });
//            if(!chatMemoryStringBuilder.isEmpty()){
//                messageStringBuilder.append(chatMemoryStringBuilder);
//            }
            String similarityFromVectorStore = getSimilarityFromVectorStore(message);
            log.info("similarityFromVectorStore:{}",similarityFromVectorStore);
            if (!similarityFromVectorStore.isBlank()) {
                messageStringBuilder.append("\n\nRelevant Context:\n").append(similarityFromVectorStore);
            }

        }
//        etlService.extractEmbeddingDataFromString(List.of(message));
        Message userMessage = new UserMessage(messageStringBuilder.toString());
        Message systemMessage = new SystemPromptTemplate(Constants.systemDefaultPromptMessage).createMessage();
        Message safeGuardDefaultSystemMessage = new SystemPromptTemplate(Constants.defaultSafeGuardSystemPromptMessage).createMessage();
        return new Prompt(List.of(systemMessage, userMessage, safeGuardDefaultSystemMessage));
    }

    public Prompt createPromptForDataExtractionFromImage(MultipartFile file, String message) {
        log.info("contentType:" + file.getContentType());
        log.info("message:" + message);
        MimeType mimeType = MediaType.valueOf(file.getContentType());
        UserMessage userMessage = UserMessage.builder()
                .text(message)
                .media(new Media(mimeType, file.getResource()))
                .metadata(new HashMap<>())
                .build();
        Message systemMessageForSafetyPurpose = new SystemPromptTemplate(Constants.defaultSystemPromptMessageForImageSafetyPurpose).createMessage();
        Message systemMessageForImageOptimization = new SystemPromptTemplate(Constants.defaultSystemPromptMessageForImageOptimization).createMessage();
        Message safeGuardDefaultSystemMessage = new SystemPromptTemplate(Constants.defaultSafeGuardSystemPromptMessage).createMessage();
        return new Prompt(List.of(userMessage, systemMessageForSafetyPurpose, systemMessageForImageOptimization, safeGuardDefaultSystemMessage));
    }

    private String getSimilarityFromVectorStore(String message) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .similarityThreshold(0.2)
                .topK(3)
                .filterExpression("userId == '" + userId + "'")
                .build();
        List<Document> documentList = vectorStore.similaritySearch(searchRequest);
        log.info("documentListSize" + documentList.size());
        StringBuilder matchingStringBuilder = new StringBuilder();
        for (Document document : documentList) {
            if (document != null && document.isText())
                matchingStringBuilder.append(document.getText()).append("\n");
        }
        return matchingStringBuilder.toString();
    }

}