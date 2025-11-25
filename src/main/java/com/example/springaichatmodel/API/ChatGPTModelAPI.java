package com.example.springaichatmodel.API;


import com.example.springaichatmodel.DTO.ChatPromptDTO;
import com.example.springaichatmodel.DTO.ImageDetailsDTO;
import com.example.springaichatmodel.DTO.ResponseMessageDTO;
import com.example.springaichatmodel.Utils.Constants;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping(value = "${api/v1}")
public interface ChatGPTModelAPI {
    //API to get the response from AImodel as a String.
    @PostMapping(value = "${getResponseAsString}")
    ResponseEntity<String> getResponseAsString(@RequestBody ChatPromptDTO chatPromptDTO);

    //API to get the response from AIModel as an Entity.
    @PostMapping(value = "${getResponseAsEntity}")
    ResponseEntity<ChatPromptDTO> getResponseAsEntity(@RequestBody ChatPromptDTO chatPromptDTO);

    //API to get the response from AIModel as Generics.
    @PostMapping(value = "${getResponseAsGenerics}")
    ResponseEntity<List<ChatPromptDTO>> getResponseAsGenerics(@RequestBody ChatPromptDTO chatPromptDTO);

    @PostMapping(value = "${newChat}")
    ResponseEntity<List<ChatPromptDTO>> newChat(@RequestBody ChatPromptDTO chatPromptDTO);

    //API to get contents in the chat memory.
    @GetMapping(value = "${getContentsInMemory}")
    ResponseEntity<List<Message>> getContentsInMemory();

    @PostMapping(value = "${generateImage}")
    ResponseEntity<List<String>> generateImageFromInstruction(@RequestParam(value = "instruction") String instruction);

    @PostMapping(value = "${uploadDocument}")
    ResponseEntity<List<ImageDetailsDTO>> getResponseByAnalysingTheMedia(@RequestParam(value = "image") MultipartFile file,
                                                                         @RequestParam(value = "instruction", required = false, defaultValue = Constants.defaultUserPromptMessageForImage) String instruction) throws IOException;

    @PostMapping(value = "${clearChatMemory}")
    ResponseEntity<ResponseMessageDTO> clearChatMemory();

    @GetMapping(value = "${getAllConversationIds}")
    ResponseEntity<List<String>> getAllConversationIds();
}
