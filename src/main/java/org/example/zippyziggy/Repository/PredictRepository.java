package org.example.zippyziggy.Repository;

import org.example.zippyziggy.Domain.Predict;
import org.example.zippyziggy.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredictRepository extends JpaRepository<Predict, Long> {
    Optional<Predict> findByUser(User user);
}
