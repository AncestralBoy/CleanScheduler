package org.project.cleanscheduler.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.cleanscheduler.utils.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private UserRole role;
    private RoommateDTO roommate;
}
