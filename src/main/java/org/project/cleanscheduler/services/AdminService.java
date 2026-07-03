package org.project.cleanscheduler.services;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.mapper.AssignmentMapper;
import org.project.cleanscheduler.database.services.interface_.AssignmentService;
import org.project.cleanscheduler.domain.dto.AssignmentDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AssignmentService assignmentService;
    private final AssignmentMapper assignmentMapper;
    private final SchedulerService schedulerService;

    public List<AssignmentDTO> getCurrentWeekAssignments() {
        LocalDate currentWeekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        return assignmentService.findAllByWeekStart(currentWeekStart)
                .stream()
                .map(assignmentMapper::toDTO)
                .toList();
    }

    public void triggerWeeklyGeneration() {
        LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        schedulerService.triggerGeneration(weekStart);
    }
}
