package com.example.llmcalling;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("api/")
public class ChatController {

    private final ChatService chatService;


    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping("chat")
    public ResponseEntity<com.example.llmcalling.ChatResponse> ask(@Valid @RequestBody ChatRequest chatRequest) {
        ChatResponse response = chatService.ask(chatRequest.prompt().trim().toLowerCase(Locale.ROOT));
        return ResponseEntity.ok(response);
    }
}
