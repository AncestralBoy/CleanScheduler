package org.project.cleanscheduler.database.repository;

import org.project.cleanscheduler.database.entity.Assignment;
import org.project.cleanscheduler.database.entity.Room;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.utils.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findTopByRoomAndStatusOrderByWeekStartDesc(
            Room room, AssignmentStatus status
    );

    Optional<Assignment> findByRoommateAndStatus(
        Roommate roommate, AssignmentStatus status
    );

    Optional<Assignment> findByWeekStartAndRoommate(
            LocalDate weekStart, Roommate roommate
    );

    List<Assignment> findAllByRoommateOrderByWeekStartDesc(Roommate roommate);
    List<Assignment> findAllByWeekStart(LocalDate weekStart);
}
