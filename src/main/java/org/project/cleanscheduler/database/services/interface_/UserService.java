package org.project.cleanscheduler.database.services.interface_;

import org.project.cleanscheduler.domain.dto.LoginDTO;
import org.project.cleanscheduler.domain.dto.LoginResponseDTO;
import org.project.cleanscheduler.domain.dto.RegisterDTO;
import org.project.cleanscheduler.domain.dto.UpdatePasswordDTO;
import org.project.cleanscheduler.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    void register(RegisterDTO dto);
    LoginResponseDTO login(LoginDTO dto);
    List<UserDTO> getAll();
    UserDTO getById(Long id);
    void delete(Long id);
    void updatePassword(Long userId, UpdatePasswordDTO dto);
}
