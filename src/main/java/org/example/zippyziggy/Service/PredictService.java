package org.example.zippyziggy.Service;

import org.example.zippyziggy.DTO.request.PredictRequest;
import org.example.zippyziggy.DTO.response.PredictResponse;
import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Domain.User;
import org.example.zippyziggy.Repository.PredictRepository;
import org.example.zippyziggy.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictService {

    private final PredictRepository predictRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public PredictService(PredictRepository predictRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.predictRepository = predictRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public Predict predict(PredictRequest request) {
        String loginUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException());

        ResponseEntity<PredictResponse> response = restTemplate.postForEntity(
                "https://onnx-fastapi-62950685798.asia-northeast3.run.app/predict",
                request,
                PredictResponse.class
        );
        double probability = response.getBody().getProbabilities().get(1);

        predictRepository.findByUser(user).ifPresent(predictRepository::delete);

        Predict predict = new Predict();
        predict.setUser(user);
        predict.setExam1_age(request.getExam1_age());
        predict.setSmoke(request.getSmoke());
        predict.setPrs(request.getPrs());
        predict.setResult(probability);

        return predictRepository.save(predict);
    }
}
