package com.spring_security_practice.AdvAuthService.service;


import com.spring_security_practice.AdvAuthService.dto.AdminUserUpdateDTO;
import com.spring_security_practice.AdvAuthService.dto.SupportUserUpdateDTO;
import com.spring_security_practice.AdvAuthService.dto.UserSelfUpdateDTO;
import com.spring_security_practice.AdvAuthService.entity.Role;
import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.exception.auth.InvalidCredentialsException;
import com.spring_security_practice.AdvAuthService.exception.user.DuplicateEmailException;
import com.spring_security_practice.AdvAuthService.exception.user.UserNotFoundException;
import com.spring_security_practice.AdvAuthService.repository.RoleRepository;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import com.spring_security_practice.AdvAuthService.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<User> getUserByEmail(String email) {
        return ResponseEntity.ok().body(userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @Override
    public ResponseEntity<Object> selfUpdateUser(UserSelfUpdateDTO dto) {
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            String username = SecurityUtils.getCurrentUserName();

            User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

            if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
                throw new InvalidCredentialsException();
            }

            if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
                throw new IllegalArgumentException("New password cannot be same as old password");
            }

            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);
            authenticationService.revokeAllUserToken(user);
            return ResponseEntity.status(HttpStatus.OK).body("User details updated");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public ResponseEntity<Object> supportUpdateUser(Long id, SupportUserUpdateDTO dto) {

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (user.getEmail().equals(dto.getEmail())) {
            throw new DuplicateEmailException();
        }

        user.setEmail(dto.getEmail());
        userRepository.save(user);
        authenticationService.revokeAllUserToken(user);
        return ResponseEntity.status(HttpStatus.OK).body("User details updated");
    }

    @Override
    public ResponseEntity<Object> adminUpdateUser(Long id, AdminUserUpdateDTO dto) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            // ---------------- EMAIL UPDATE ----------------
            if (dto.getEmail() != null) {

                String newEmail = dto.getEmail().trim().toLowerCase();

                if (user.getEmail().equals(newEmail)) {
                    throw new DuplicateEmailException();
                }

                boolean emailExists = userRepository.existsByEmail(newEmail);
                if (emailExists) {
                    throw new DuplicateEmailException();
                }

                user.setEmail(newEmail);
            }

            // ---------------- PASSWORD UPDATE ----------------
            if (dto.getPassword() != null) {

                if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                    throw new IllegalArgumentException("New password cannot be same as old password");
                }

                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            // ---------------- ROLE UPDATE ----------------
            if (dto.getRole() != null) {

                Role newRole = roleRepository.findByName(dto.getRole())
                        .orElseThrow(() ->
                                new RoleNotFoundException("Role not found: " + dto.getRole())
                        );

                boolean isCurrentlyAdmin = user.getRoles()
                        .stream()
                        .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

                boolean isNewRoleAdmin = newRole.getName().equals("ROLE_ADMIN");

                if (isCurrentlyAdmin && !isNewRoleAdmin) {
                    long adminCount = userRepository.countByRoles_Name("ROLE_ADMIN");

                    if (adminCount <= 1) {
                        throw new IllegalStateException("Cannot remove the last ADMIN");
                    }
                }

                user.getRoles().clear();

                user.getRoles().add(newRole);
            }

            userRepository.save(user);

            authenticationService.revokeAllUserToken(user);
            return ResponseEntity.status(HttpStatus.OK).body("User details updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
