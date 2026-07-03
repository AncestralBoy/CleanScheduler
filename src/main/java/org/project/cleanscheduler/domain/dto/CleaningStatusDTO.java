package org.project.cleanscheduler.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleaningStatusDTO {
    private AssignmentDTO pendingAssignment;
    private List<AssignmentDTO> completedHistory;
    private int completedCount;
    private int abandonedCount;
}
