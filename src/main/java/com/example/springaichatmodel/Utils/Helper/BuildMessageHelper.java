package com.example.springaichatmodel.Utils.Helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildMessageHelper {
    private String role;
    private String text;
    private Map<String, Object> metadata;

    public Message buildMessage(){
        switch(this.role){
            case "user" -> {
                return new UserMessage(this.text);
            }
            case "assistant" -> {
                return new AssistantMessage(this.text, this.metadata);
            }

            default -> {
                return new SystemMessage(this.text);
            }
        }
    }
}
