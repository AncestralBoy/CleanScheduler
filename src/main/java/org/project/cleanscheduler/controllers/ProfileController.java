package org.project.cleanscheduler.controllers;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.User;
import org.project.cleanscheduler.database.services.interface_.RoommateService;
import org.project.cleanscheduler.database.services.interface_.UserService;
import org.project.cleanscheduler.domain.dto.UpdatePasswordDTO;
import org.project.cleanscheduler.domain.dto.UpdateProfileDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class ProfileController {

    private final RoommateService roommateService;
    private final UserService userService;

    @PatchMapping("/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@AuthenticationPrincipal User user,
                              @RequestBody UpdateProfileDTO dto) {
        roommateService.updateProfile(user.getRoommate().getId(), dto);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@AuthenticationPrincipal User user,
                               @RequestBody UpdatePasswordDTO dto) {
        userService.updatePassword(user.getId(), dto);
    }
}
