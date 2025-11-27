package com.example.springaichatmodel.API;


import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping(value = "${api/v1/chat}")
public interface ChatGPTModelAPI {
    //API to get the response from AImodel as a String.
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "${getResponseAsString}")
    ResponseEntity<String> getResponseAsString(@RequestBody ChatPromptDTO chatPromptDTO,
                                               @RequestParam(value = "conversationId") String conversationId);

    //API to get the response from AIModel as an Entity.
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "${getResponseAsEntity}")
    ResponseEntity<ChatPromptDTO> getResponseAsEntity(@RequestBody ChatPromptDTO chatPromptDTO,
                                                      @RequestParam(value = "conversationId") String conversationId);

    //API to get the response from AIModel as Generics.
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "${getResponseAsGenerics}")
    ResponseEntity<List<ChatPromptDTO>> getResponseAsGenerics(@RequestBody ChatPromptDTO chatPromptDTO,
                                                              @RequestParam(value = "conversationId") String conversationId);

    @PostMapping(value = "${newChat}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<List<ChatPromptDTO>> newChat(@RequestBody ChatPromptDTO chatPromptDTO);

    //API to get contents in the chat memory.
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "${getContentsInMemory}")
    ResponseEntity<List<Message>> getContentsInMemory(@RequestParam(value = "conversationId")  String conversationId);

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "${generateImage}")
    ResponseEntity<List<String>> generateImageFromInstruction(@RequestParam(value = "instruction") String instruction);

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "${uploadDocument}")
    ResponseEntity<List<ImageDetailsDTO>> getResponseByAnalysingTheMedia(@RequestParam(value = "image") MultipartFile file,
                                                                         @RequestParam(value = "instruction", required = false, defaultValue = Constants.defaultUserPromptMessageForImage) String instruction) throws IOException;
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "${clearChatMemory}")
    ResponseEntity<ResponseMessageDTO> clearChatMemory(@RequestParam(value = "conversationId")  String conversationId);

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "${getAllConversationIds}")
    ResponseEntity<List<String>> getAllConversationIds();
}
