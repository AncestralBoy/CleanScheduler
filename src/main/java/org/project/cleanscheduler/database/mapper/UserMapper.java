package org.project.cleanscheduler.database.mapper;

import org.mapstruct.Mapper;
import org.project.cleanscheduler.database.entity.User;
import org.project.cleanscheduler.domain.dto.UserDTO;

@Mapper(componentModel = "spring", uses = {RoommateMapper.class})
public interface UserMapper {
    UserDTO toDTO(User entity);
}
