package org.example.zippyziggy.Controller;

import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Domain.Magazine;
import org.example.zippyziggy.Repository.PredictRepository;
import org.example.zippyziggy.Repository.MagazineRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendationController {

    private final PredictRepository predictRepository;
    private final MagazineRepository magazineRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Magazine>> recommendMagazines(@PathVariable String userId) {
        Predict predict = predictRepository.findTopByUser_UserIdOrderByIdDesc(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자의 예측 결과가 없습니다."));


        double result = predict.getResult();
        int smoke = predict.getSmoke();

        List<Magazine> recommended = new ArrayList<>();

        // result 기반 매거진 추천
        if (result < 25.0) {
            recommended.add(magazineRepository.findById(1L).orElse(null));
        } else if (result < 50.0) {
            recommended.add(magazineRepository.findById(2L).orElse(null));
        } else if (result < 75.0) {
            recommended.add(magazineRepository.findById(3L).orElse(null));
        } else {
            recommended.add(magazineRepository.findById(4L).orElse(null));
        }

        // smoke 기반 매거진 추천
        if (smoke == 1) {
            recommended.add(magazineRepository.findById(5L).orElse(null));
        } else {
            recommended.add(magazineRepository.findById(6L).orElse(null));
        }

        return ResponseEntity.ok(recommended);
    }
}
