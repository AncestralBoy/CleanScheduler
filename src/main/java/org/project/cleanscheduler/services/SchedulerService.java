package org.project.cleanscheduler.services;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.database.services.interface_.RoommateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final RotationService rotationService;
    private final RoommateService roommateService;

    @Scheduled(cron = "0 0 8 * * MON")
    public void generateWeeklyAssignments() {
        triggerGeneration(LocalDate.now().with(DayOfWeek.MONDAY));
    }

    public void triggerGeneration(LocalDate weekStart) {
        List<Roommate> activeRoommates = roommateService.getAllActive().stream().toList();
        if (activeRoommates.isEmpty()) {
            return;
        }
        rotationService.generateWeeklyAssignments(activeRoommates, weekStart);
    }
}
