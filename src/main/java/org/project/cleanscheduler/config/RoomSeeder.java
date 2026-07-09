package org.project.cleanscheduler.config;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.Room;
import org.project.cleanscheduler.database.repository.RoomRepository;
import org.project.cleanscheduler.domain.dto.enums.RoomType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;

    @Override
    public void run(String... args) {
        for (RoomType type : RoomType.values()) {
            if (roomRepository.getRoomByName(type.getDisplayName()) == null) {
                Room room = Room.builder()
                        .name(type.getDisplayName())
                        .score(type.getScore())
                        .isAssignedThisWeek(false)
                        .build();
                roomRepository.save(room);
            }
        }
    }
}
