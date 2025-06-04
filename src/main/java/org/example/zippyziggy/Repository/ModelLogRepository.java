package org.example.zippyziggy.Repository;

import org.example.zippyziggy.Domain.ModelLog;
import org.example.zippyziggy.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ModelLogRepository extends JpaRepository<ModelLog, Long> {
    Optional<ModelLog> findByUserAndTestDate(User user, LocalDate testDate);
    List<ModelLog> findAllByUser(User user);
}
