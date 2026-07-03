package org.project.cleanscheduler.database.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.database.mapper.RoommateMapper;
import org.project.cleanscheduler.database.repository.RoommateRepository;
import org.project.cleanscheduler.database.services.interface_.RoommateService;
import org.project.cleanscheduler.domain.dto.RoommateDTO;
import org.project.cleanscheduler.domain.dto.UpdateProfileDTO;
import org.project.cleanscheduler.exception.AlreadyExistException;
import org.project.cleanscheduler.exception.EntityNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoommateServiceImpl implements RoommateService {

    private final RoommateRepository roommateRepository;
    private final RoommateMapper roommateMapper;

    @Override
    public Optional<Roommate> getAllActive() {
        return roommateRepository.findByIsActiveTrue();
    }

    @Override
    public List<RoommateDTO> getLeaderboard() {
        log.info("Fetching leaderboard");
        return roommateRepository.findAllByIsActiveTrueOrderByCleanPointsDesc()
                .stream()
                .map(roommateMapper::toDTO)
                .toList();
    }

    @Override
    public void toggleActive(Long roommateId, boolean active) {
        log.info("Setting roommate id {} active={}", roommateId, active);
        Roommate roommate = roommateRepository.findById(roommateId)
                .orElseThrow(() -> new EntityNotExistException(
                        String.format("Roommate with id %d doesn't exist.", roommateId)
                ));
        roommate.setActive(active);
        roommateRepository.save(roommate);
    }

    @Override
    public void updateProfile(Long roommateId, UpdateProfileDTO dto) {
        log.info("Updating profile for roommate id: {}", roommateId);

        Roommate roommate = roommateRepository.findById(roommateId)
                .orElseThrow(() -> new EntityNotExistException(
                        String.format("Roommate with id %d doesn't exist.", roommateId)
                ));

        if (!roommate.getNickname().equals(dto.getNickname())
                && roommateRepository.existsByNickname(dto.getNickname())) {
            throw new AlreadyExistException(
                    String.format("Nickname '%s' is already taken.", dto.getNickname())
            );
        }

        roommate.setName(dto.getName());
        roommate.setSurname(dto.getSurname());
        roommate.setNickname(dto.getNickname());
        roommateRepository.save(roommate);
        log.info("Profile updated successfully for roommate id: {}", roommateId);
    }
}
