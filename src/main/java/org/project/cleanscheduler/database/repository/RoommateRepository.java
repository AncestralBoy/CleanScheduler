package org.project.cleanscheduler.database.repository;

import org.project.cleanscheduler.database.entity.Roommate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoommateRepository extends JpaRepository<Roommate, Long> {
    Optional<Roommate> findByIsActiveTrue();
    boolean existsByNickname(String nickname);
    List<Roommate> findAllByIsActiveTrueOrderByCleanPointsDesc();
}
