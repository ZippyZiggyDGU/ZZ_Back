package org.example.zippyziggy.Controller;

import org.example.zippyziggy.DTO.request.PredictRequest;
import org.example.zippyziggy.DTO.response.PredictResponse;
import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Service.PredictService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class PredictController {

    private final PredictService predictService;

    public PredictController(PredictService predictService) {
        this.predictService = predictService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictResponse> predict(@RequestBody PredictRequest request) {

        Predict predict = predictService.predict(request);

        double positiveP = predict.getResult();
        double negativeP = 1.0 - positiveP;

        int label = positiveP >= 0.5 ? 1 : 0;

        PredictResponse response = new PredictResponse(
                label,
                Arrays.asList(negativeP, positiveP)
        );

        return ResponseEntity.ok(response);
    }

}
