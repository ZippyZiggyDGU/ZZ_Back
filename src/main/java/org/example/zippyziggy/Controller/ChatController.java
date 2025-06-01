package org.example.zippyziggy.Controller;

import org.example.zippyziggy.Service.ChatbotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatbotService chatbotService;

    @Autowired
    public ChatController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping
    public ResponseEntity<?> askChat(@RequestBody Map<String, String> request) {
        String intent = request.get("intent");
        String question = request.get("question");

        String answer = chatbotService.getChatbotAnswer(intent, question);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
