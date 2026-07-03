package org.project.cleanscheduler.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoommateDTO {
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private int cleanPoints;
    private boolean isActive;
}
