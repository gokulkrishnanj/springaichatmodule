package com.example.springaichatmodel.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatPromptDTO {
    private String question;
    private String answer;
}
