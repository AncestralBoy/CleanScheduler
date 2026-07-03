package org.project.cleanscheduler.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.cleanscheduler.utils.AssignmentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentStatusUpdateDTO {
    private AssignmentStatus status;
}
