package com.example.springaichatmodel.Document;

import com.example.springaichatmodel.Model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "UserChatMemory")
public class ChatMemoryDocument {

    @Id
    private String id;

    private String userId;

    private String conversationId;

    private List<ChatMessage>  chatMessages;
}
