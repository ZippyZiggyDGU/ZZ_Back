package org.example.zippyziggy.Repository;

import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredictRepository extends JpaRepository<Predict, Long> {

    // 이미 있는 메서드
    Optional<Predict> findByUser(User user);

    // 새로 추가: userId 기준으로 가장 최신 Predict 한 개 조회
    Optional<Predict> findTopByUser_UserIdOrderByIdDesc(String userId);

}
