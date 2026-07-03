package org.project.cleanscheduler.controllers;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.services.interface_.RoommateService;
import org.project.cleanscheduler.domain.dto.AssignmentDTO;
import org.project.cleanscheduler.domain.dto.ToggleActiveDTO;
import org.project.cleanscheduler.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final RoommateService roommateService;

    @GetMapping("/assignments/current-week")
    public List<AssignmentDTO> getCurrentWeekAssignments() {
        return adminService.getCurrentWeekAssignments();
    }

    @PostMapping("/generate-assignments")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void generateAssignments() {
        adminService.triggerWeeklyGeneration();
    }

    @PatchMapping("/users/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleActive(@PathVariable Long id, @RequestBody ToggleActiveDTO dto) {
        roommateService.toggleActive(id, dto.isActive());
    }
}
