package org.project.cleanscheduler.database.services.interface_;

import org.project.cleanscheduler.database.entity.Assignment;
import org.project.cleanscheduler.database.entity.Room;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.utils.AssignmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    void createAssignment(Assignment assignment);
    public void updateAssignment(Assignment assignment);
    void updateAssignments(List<Assignment> assignments);
    Optional<Assignment> findByRoommateAndStatus(Roommate roommate, AssignmentStatus status);
    Optional<Assignment> findTopByRoomAndStatusOrderByWeekStartDesc(Room room, AssignmentStatus status);
    List<Assignment> findAllByRoommate(Roommate roommate);
    List<Assignment> findAllByWeekStart(LocalDate weekStart);
    void updateStatus(Long assignmentId, AssignmentStatus newStatus, Roommate roommate);
}
