package com.example.springaichatmodel.Utils;

import java.util.List;

public class Constants {
    public static final List<String> restrictedWordsList = List.of("Bomb", "Terrorist", "Kill", "Suicide", "Rape", "Nudity", "Nude");
    public static final String systemDefaultPromptMessage = "If the request is empty consider user asking who are you(your name is Google Gemini) and respond who you are and what are you capable of. If the request is like statement just listen and respond. If the request contains any grammar or spelling mistakes add the correct request in the searchInstead field. If you do have information about request in memory respond with that";
    public static final String defaultConversationId = "42";
}
