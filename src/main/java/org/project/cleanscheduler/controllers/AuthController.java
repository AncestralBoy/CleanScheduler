package org.project.cleanscheduler.controllers;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.services.interface_.UserService;
import org.project.cleanscheduler.domain.dto.LoginDTO;
import org.project.cleanscheduler.domain.dto.LoginResponseDTO;
import org.project.cleanscheduler.domain.dto.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }
}
