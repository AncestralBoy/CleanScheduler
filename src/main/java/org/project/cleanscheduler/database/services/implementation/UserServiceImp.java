package org.project.cleanscheduler.database.services.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.database.entity.User;
import org.project.cleanscheduler.database.repository.RoommateRepository;
import org.project.cleanscheduler.database.repository.UserRepository;
import org.project.cleanscheduler.config.JwtService;
import org.project.cleanscheduler.database.mapper.UserMapper;
import org.project.cleanscheduler.database.services.interface_.UserService;
import org.project.cleanscheduler.domain.dto.LoginDTO;
import org.project.cleanscheduler.domain.dto.LoginResponseDTO;
import org.project.cleanscheduler.domain.dto.RegisterDTO;
import org.project.cleanscheduler.domain.dto.UpdatePasswordDTO;
import org.project.cleanscheduler.domain.dto.UserDTO;
import org.project.cleanscheduler.exception.AlreadyExistException;
import org.project.cleanscheduler.exception.EntityNotExistException;
import org.project.cleanscheduler.utils.UserRole;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoommateRepository roommateRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void register(RegisterDTO dto) {
        log.info("Registering new user with nickname: {}", dto.getNickname());

        if (userRepository.existsByRoommateNickname(dto.getNickname())) {
            throw new AlreadyExistException(
                    String.format("User with nickname '%s' already exists.", dto.getNickname())
            );
        }

        Roommate roommate = Roommate.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .nickname(dto.getNickname())
                .cleanPoints(0)
                .isActive(true)
                .build();
        roommateRepository.save(roommate);

        User user = User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRole.ROOMMATE)
                .roommate(roommate)
                .build();
        userRepository.save(user);

        log.info("User registered successfully with id: {}", user.getId());
    }

    @Override
    public LoginResponseDTO login(LoginDTO dto) {
        log.info("Login attempt for nickname: {}", dto.getNickname());

        User user = (User) loadUserByUsername(dto.getNickname());

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        String token = jwtService.generateToken(user);
        log.info("Login successful for nickname: {}", dto.getNickname());
        return new LoginResponseDTO(token);
    }

    @Override
    public List<UserDTO> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotExistException(
                        String.format("User with id %d doesn't exist.", id)
                ));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistException(
                        String.format("User with id %d doesn't exist.", id)
                ));
        userRepository.delete(user);
        log.info("User with id {} deleted successfully.", id);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, UpdatePasswordDTO dto) {
        log.info("Updating password for user id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotExistException(
                        String.format("User with id %d doesn't exist.", userId)
                ));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        log.info("Password updated successfully for user id: {}", userId);
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return userRepository.findByRoommateNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with nickname '%s' not found.", nickname)
                ));
    }
}
