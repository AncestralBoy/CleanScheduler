package org.project.cleanscheduler.database.services.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cleanscheduler.database.entity.Assignment;
import org.project.cleanscheduler.database.entity.Room;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.database.repository.AssignmentRepository;
import org.project.cleanscheduler.database.repository.RoommateRepository;
import org.project.cleanscheduler.database.services.interface_.AssignmentService;
import org.project.cleanscheduler.exception.AlreadyExistException;
import org.project.cleanscheduler.exception.EntityNotExistException;
import org.project.cleanscheduler.exception.InvalidOperationException;
import org.project.cleanscheduler.utils.AssignmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentServiceImp implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final RoommateRepository roommateRepository;

    @Override
    @Transactional
    public void createAssignment(Assignment assignment) {
        log.info("Creating new assignment for roommate: {}", assignment.getRoommate().getName());

        if(assignmentRepository.findByWeekStartAndRoommate(assignment.getWeekStart(), assignment.getRoommate()).isPresent()){
            throw new AlreadyExistException(String.format(
                    "Assignment for roommate: %s, for week %s, already exists.",
                    assignment.getRoommate().getName(), assignment.getWeekStart()
            ));
        }

        assignmentRepository.save(assignment);
        log.info("Assignment created successfully with id: {}", assignment.getId());
    }

    @Override
    public void updateAssignment(Assignment assignment) {
        log.info("Updating assignment for roommate: {}, week: {}", assignment.getRoommate().getName(), assignment.getWeekStart());

        assignmentRepository.findById(assignment.getId())
                .orElseThrow(
                        () -> new EntityNotExistException(
                                String.format("Assignment with id: %d doesn't exists.", assignment.getId())
                        )
                );

        assignmentRepository.save(assignment);
        log.info("Assignment updated successfully.");
    }

    @Override
    @Transactional
    public void updateAssignments(List<Assignment> assignments) {
        log.info("Updating {} assignments", assignments.size());

        List<Long> ids = assignments.stream()
                .map(Assignment::getId)
                .collect(Collectors.toList());

        List<Long> existingIds = assignmentRepository.findAllById(ids)
                .stream()
                .map(Assignment::getId)
                .toList();

        List<Long> missingIds = ids.stream()
                .filter(id -> !existingIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new EntityNotExistException(
                    String.format("Assignments with ids %s don't exist.", missingIds)
            );
        }

        assignmentRepository.saveAll(assignments);
        log.info("Updated {} assignments successfully.", assignments.size());
    }

    @Override
    public Optional<Assignment> findByRoommateAndStatus(Roommate roommate, AssignmentStatus status){
        log.info("Finding assignment for roommate: {} with status: {}", roommate.getName(), status);
        return assignmentRepository.findByRoommateAndStatus(roommate, status);
    }

    public Optional<Assignment> findTopByRoomAndStatusOrderByWeekStartDesc(Room room, AssignmentStatus status){
        log.info("Finding assignment for room: {} with status: {}", room.getName(), status);
        return assignmentRepository.findTopByRoomAndStatusOrderByWeekStartDesc(room, status);
    }

    @Override
    public List<Assignment> findAllByRoommate(Roommate roommate) {
        log.info("Finding all assignments for roommate: {}", roommate.getName());
        return assignmentRepository.findAllByRoommateOrderByWeekStartDesc(roommate);
    }

    @Override
    public List<Assignment> findAllByWeekStart(LocalDate weekStart) {
        log.info("Finding all assignments for week starting: {}", weekStart);
        return assignmentRepository.findAllByWeekStart(weekStart);
    }

    @Override
    @Transactional
    public void updateStatus(Long assignmentId, AssignmentStatus newStatus, Roommate roommate) {
        if (newStatus != AssignmentStatus.COMPLETED && newStatus != AssignmentStatus.ABANDONED) {
            throw new InvalidOperationException("Status can only be updated to COMPLETED or ABANDONED.");
        }

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotExistException(
                        String.format("Assignment with id %d doesn't exist.", assignmentId)
                ));

        if (!assignment.getRoommate().getId().equals(roommate.getId())) {
            throw new EntityNotExistException(
                    String.format("Assignment with id %d doesn't exist.", assignmentId)
            );
        }

        if (assignment.getStatus() != AssignmentStatus.PENDING) {
            throw new InvalidOperationException(
                    String.format("Assignment with id %d is not in PENDING status.", assignmentId)
            );
        }

        assignment.setStatus(newStatus);
        assignmentRepository.save(assignment);

        if (newStatus == AssignmentStatus.COMPLETED) {
            Roommate assignee = assignment.getRoommate();
            assignee.setCleanPoints(assignee.getCleanPoints() + assignment.getRoom().getScore());
            roommateRepository.save(assignee);
            log.info("Roommate {} earned {} points. Total: {}", assignee.getName(), assignment.getRoom().getScore(), assignee.getCleanPoints());
        }

        log.info("Assignment {} updated to status {} by roommate {}", assignmentId, newStatus, roommate.getName());
    }
}
