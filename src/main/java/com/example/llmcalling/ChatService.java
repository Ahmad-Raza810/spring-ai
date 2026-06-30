package com.example.llmcalling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ChatService {

    private final ChatClient chatClient;
    private final String apiKey;

    public ChatService(ChatClient.Builder chatClientBuilder, @Value("${spring.ai.google.genai.api-key}") String apiKey) {
        this.chatClient = chatClientBuilder.build();
        this.apiKey = apiKey;
        log.info(this.apiKey);
    }

    public Flux<String> ask(String prompt) {

        long start = System.currentTimeMillis();

        log.info(prompt);
        log.info(apiKey);
           Flux<String> stream=chatClient.prompt()
                    .system("""
                            Answer in no more than 100 words.
                            Finish your response with a complete sentence.
                            Never stop mid-sentence.
                           """)
                    .user(prompt)
                    .stream()
                    .content();

        long end = System.currentTimeMillis();

        System.out.println("LLM Response Time : " + (end - start) + " ms");
        return stream;
    }
}
