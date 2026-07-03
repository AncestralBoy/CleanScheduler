package org.project.cleanscheduler.database.mapper;

import org.mapstruct.Mapper;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.domain.dto.RoommateDTO;

@Mapper(componentModel = "spring")
public interface RoommateMapper {
    Roommate toEntity(RoommateDTO dto);
    RoommateDTO toDTO(Roommate entity);
}
