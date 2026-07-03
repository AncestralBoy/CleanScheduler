package org.project.cleanscheduler.controllers;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.User;
import org.project.cleanscheduler.database.services.interface_.AssignmentService;
import org.project.cleanscheduler.domain.dto.AssignmentStatusUpdateDTO;
import org.project.cleanscheduler.domain.dto.CleaningStatusDTO;
import org.project.cleanscheduler.services.CleaningStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class CleaningStatusController {

    private final CleaningStatusService cleaningStatusService;
    private final AssignmentService assignmentService;

    @GetMapping("/cleaning-status")
    public CleaningStatusDTO getCleaningStatus(@AuthenticationPrincipal User user) {
        return cleaningStatusService.getStatus(user.getRoommate());
    }

    @PatchMapping("/assignments/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAssignmentStatus(@AuthenticationPrincipal User user,
                                       @PathVariable Long id,
                                       @RequestBody AssignmentStatusUpdateDTO dto) {
        assignmentService.updateStatus(id, dto.getStatus(), user.getRoommate());
    }
}
