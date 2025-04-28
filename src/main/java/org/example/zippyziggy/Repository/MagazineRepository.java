package org.example.zippyziggy.Repository;

import org.example.zippyziggy.Domain.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
}
