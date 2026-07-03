package org.project.cleanscheduler.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.cleanscheduler.utils.AssignmentStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private Long id;
    private RoommateDTO roommateDTO;
    private RoomDTO room;
    private LocalDate weekStart;
    private AssignmentStatus status;
}
