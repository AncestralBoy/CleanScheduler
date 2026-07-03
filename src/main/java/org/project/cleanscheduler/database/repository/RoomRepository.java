package org.project.cleanscheduler.database.repository;

import org.project.cleanscheduler.database.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room getRoomByName(String name);
}
