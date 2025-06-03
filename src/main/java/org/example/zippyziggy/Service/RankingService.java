package org.example.zippyziggy.Service;

import org.example.zippyziggy.DTO.response.RankResponse;
import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Domain.User;
import org.example.zippyziggy.Repository.PredictRepository;
import org.example.zippyziggy.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService {

    private final PredictRepository predictRepository;
    private final UserRepository userRepository;

    public RankingService(PredictRepository predictRepository, UserRepository userRepository) {
        this.predictRepository = predictRepository;
        this.userRepository = userRepository;
    }

    public List<RankResponse> getRanking() {
        String loginUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        Long loginUserDbId = loginUser.getId();

        List<Predict> sorted = predictRepository.findAllByOrderByResultAsc();
        int userRank = -1;

        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getUser().getId().equals(loginUserDbId)) {
                userRank = i + 1;
                break;
            }
        }

        List<Predict> top3 = predictRepository.findTop3ByOrderByResultAsc();
        Predict bottomPredict = predictRepository.findTopByOrderByResultDesc()
                .orElseThrow(() -> new IllegalArgumentException(""));

        int totalCount = sorted.size();

        List<RankResponse> result = new ArrayList<>();
        if (top3.size() > 0) result.add(new RankResponse(1, top3.get(0).getUser().getUserName()));
        if (top3.size() > 1) result.add(new RankResponse(2, top3.get(1).getUser().getUserName()));
        if (top3.size() > 2) result.add(new RankResponse(3, top3.get(2).getUser().getUserName()));

        result.add(new RankResponse(userRank, loginUser.getUserName()));
        result.add(new RankResponse(totalCount, bottomPredict.getUser().getUserName()));

        return result;
    }

}
