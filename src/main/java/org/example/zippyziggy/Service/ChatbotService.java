package org.example.zippyziggy.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class ChatbotService {

    @Autowired
    private RestTemplate restTemplate;

    public String getChatbotAnswer(String intent, String question) {
        String url = "http://localhost:8000/chat";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("intent", intent);
        requestBody.put("question", question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().get("answer").toString();
        } else {
            return "챗봇 응답 오류";
        }
    }
}
