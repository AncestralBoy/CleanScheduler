package org.project.cleanscheduler.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.cleanscheduler.database.entity.Assignment;
import org.project.cleanscheduler.domain.dto.AssignmentDTO;

@Mapper(componentModel = "spring", uses = {RoommateMapper.class, RoomMapper.class})
public interface AssignmentMapper {

    @Mapping(target = "roommate", source = "roommateDTO")
    Assignment toEntity(AssignmentDTO dto);

    @Mapping(target = "roommateDTO", source = "roommate")
    AssignmentDTO toDTO(Assignment entity);
}
