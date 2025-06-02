package org.example.zippyziggy.Controller;

import lombok.Getter;
import org.example.zippyziggy.DTO.response.RankResponse;
import org.example.zippyziggy.Service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/rank")
    public ResponseEntity<List<RankResponse>> getRanking() {
        return ResponseEntity.ok(rankingService.getRanking());
    }

}
