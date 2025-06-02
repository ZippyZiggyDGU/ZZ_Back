package org.example.zippyziggy.Controller;

import org.example.zippyziggy.DTO.request.PredictRequest;
import org.example.zippyziggy.DTO.response.PredictResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PredictController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String model = "https://onnx-fastapi-62950685798.asia-northeast3.run.app/predict";

    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(@RequestBody PredictRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PredictRequest> requestE = new HttpEntity<>(request, headers);

        ResponseEntity<PredictResponse> response = restTemplate
                .postForEntity(model, requestE, PredictResponse.class);

        return ResponseEntity.ok(response.getBody());
    }

}
