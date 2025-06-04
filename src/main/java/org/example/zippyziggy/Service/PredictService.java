package org.example.zippyziggy.Service;

import org.example.zippyziggy.DTO.request.PredictRequest;
import org.example.zippyziggy.DTO.response.ModelLogResponse;
import org.example.zippyziggy.DTO.response.PredictResponse;
import org.example.zippyziggy.Domain.ModelLog;
import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Domain.User;
import org.example.zippyziggy.Repository.ModelLogRepository;
import org.example.zippyziggy.Repository.PredictRepository;
import org.example.zippyziggy.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class PredictService {

    private final PredictRepository predictRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ModelLogRepository modelLogRepository;

    public PredictService(PredictRepository predictRepository,
                          UserRepository userRepository,
                          RestTemplate restTemplate,
                          ModelLogRepository modelLogRepository) {
        this.predictRepository = predictRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.modelLogRepository = modelLogRepository;
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
        Predict saved = predictRepository.save(predict);

        LocalDate today = LocalDate.now();
        modelLogRepository.findByUserAndTestDate(user, today)
                .ifPresent(modelLogRepository::delete);

        ModelLog log = ModelLog.builder()
                .user(user)
                .testDate(LocalDate.now())
                .result(probability)
                .build();
        modelLogRepository.save(log);

        return saved;
    }

    public List<ModelLogResponse> getModelLog() {
        String loginUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        List<ModelLog> logs = modelLogRepository.findAllByUser(user);

        return logs.stream()
                .map(log -> new ModelLogResponse(log.getTestDate(), log.getResult()))
                .toList();
    }
}
