package org.project.cleanscheduler.controllers;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.services.interface_.RoommateService;
import org.project.cleanscheduler.domain.dto.RoommateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final RoommateService roommateService;

    @GetMapping
    public List<RoommateDTO> getLeaderboard() {
        return roommateService.getLeaderboard();
    }
}
