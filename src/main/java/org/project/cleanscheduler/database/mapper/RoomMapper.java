package org.project.cleanscheduler.database.mapper;

import org.mapstruct.Mapper;
import org.project.cleanscheduler.database.entity.Room;
import org.project.cleanscheduler.domain.dto.RoomDTO;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toEntity(RoomDTO dto);
    RoomDTO toDTO(Room entity);
}
