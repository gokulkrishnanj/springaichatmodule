package com.example.springaichatmodel.Repository;

import com.example.springaichatmodel.Document.ChatMemoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatMemoryRepository extends MongoRepository<ChatMemoryDocument, String> {

    ChatMemoryDocument findByUserIdAndConversationId(String userId, String conversationId);

    void deleteChatMemoryDocumentByUserIdAndConversationId(String userId, String conversationId);

    List<ChatMemoryDocument> findByUserId(String userId);
}
