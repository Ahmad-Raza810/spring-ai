package com.example.llmcalling;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@RequestMapping("api/")
public class ChatController {

    private final ChatService chatService;


    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping(value = "chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ask(@Valid @RequestBody ChatRequest chatRequest) {
        return chatService.ask(chatRequest.prompt().trim().toLowerCase(Locale.ROOT)).doOnNext(
                chunk->{
                    System.out.println(LocalDateTime.now());
                    System.out.println(chunk);
                }
        );

    }

    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<String>> stream() {
        return new ResponseEntity<>(Flux.just("Hello", "How", "Are").delayElements(Duration.ofSeconds(2)), HttpStatus.OK);
    }
}
