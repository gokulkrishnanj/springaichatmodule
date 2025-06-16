package com.example.springaichatmodel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatPromptDTO {
    private String question;
    private String searchInstead;
    private String answer;
    private String referenceLink;
}
