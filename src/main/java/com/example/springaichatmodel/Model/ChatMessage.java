package com.example.springaichatmodel.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String role;
    private String content;
    private Map<String,Object> metadata;

    public ChatMessage(Message message){
        this.role= message.getMessageType().getValue();
        this.content= message.getText();
        this.metadata= message.getMetadata();
    }
}
