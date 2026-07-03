package org.project.cleanscheduler.database.services.interface_;

import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.domain.dto.RoommateDTO;
import org.project.cleanscheduler.domain.dto.UpdateProfileDTO;

import java.util.List;
import java.util.Optional;

public interface RoommateService {
    Optional<Roommate> getAllActive();
    List<RoommateDTO> getLeaderboard();
    void updateProfile(Long roommateId, UpdateProfileDTO dto);
    void toggleActive(Long roommateId, boolean active);
}
