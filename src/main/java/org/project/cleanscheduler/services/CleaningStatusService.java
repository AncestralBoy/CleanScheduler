package org.project.cleanscheduler.services;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.Assignment;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.database.mapper.AssignmentMapper;
import org.project.cleanscheduler.database.services.interface_.AssignmentService;
import org.project.cleanscheduler.domain.dto.AssignmentDTO;
import org.project.cleanscheduler.domain.dto.CleaningStatusDTO;
import org.project.cleanscheduler.utils.AssignmentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CleaningStatusService {

    private final AssignmentService assignmentService;
    private final AssignmentMapper assignmentMapper;

    public CleaningStatusDTO getStatus(Roommate roommate) {
        List<Assignment> all = assignmentService.findAllByRoommate(roommate);

        Optional<AssignmentDTO> pending = all.stream()
                .filter(a -> a.getStatus() == AssignmentStatus.PENDING)
                .findFirst()
                .map(assignmentMapper::toDTO);

        List<AssignmentDTO> completedHistory = all.stream()
                .filter(a -> a.getStatus() == AssignmentStatus.COMPLETED)
                .map(assignmentMapper::toDTO)
                .toList();

        int abandonedCount = (int) all.stream()
                .filter(a -> a.getStatus() == AssignmentStatus.ABANDONED)
                .count();

        return CleaningStatusDTO.builder()
                .pendingAssignment(pending.orElse(null))
                .completedHistory(completedHistory)
                .completedCount(completedHistory.size())
                .abandonedCount(abandonedCount)
                .build();
    }
}
