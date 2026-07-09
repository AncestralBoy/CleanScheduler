package org.project.cleanscheduler.controllers;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.User;
import org.project.cleanscheduler.database.services.interface_.RoommateService;
import org.project.cleanscheduler.database.services.interface_.UserService;
import org.project.cleanscheduler.domain.dto.ToggleActiveDTO;
import org.project.cleanscheduler.domain.dto.UpdatePasswordDTO;
import org.project.cleanscheduler.domain.dto.UpdateProfileDTO;
import org.project.cleanscheduler.domain.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class ProfileController {

    private final RoommateService roommateService;
    private final UserService userService;

    @GetMapping
    public UserDTO getMe(@AuthenticationPrincipal User user) {
        return userService.getById(user.getId());
    }

    @PatchMapping("/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@AuthenticationPrincipal User user,
                              @RequestBody UpdateProfileDTO dto) {
        roommateService.updateProfile(user.getRoommate().getId(), dto);
    }

    @PatchMapping("/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateActive(@AuthenticationPrincipal User user,
                             @RequestBody ToggleActiveDTO dto) {
        roommateService.toggleActive(user.getRoommate().getId(), dto.isActive());
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@AuthenticationPrincipal User user,
                               @RequestBody UpdatePasswordDTO dto) {
        userService.updatePassword(user.getId(), dto);
    }
}
