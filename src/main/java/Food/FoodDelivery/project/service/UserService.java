package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.*;
import Food.FoodDelivery.project.DTO.ResponseDTO.UserResponseDTO;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Mapper.UsersMapper;
import Food.FoodDelivery.project.Repository.*;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Role role = roleRepository.findByIdAndIsActive(userRequestDTO.getRoleId(), true)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + userRequestDTO.getRoleId() + " or role is not active"));

        Users user = usersMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);

        Users savedUser = userRepository.save(user);
        return usersMapper.toDto(savedUser);
    }

    @Transactional
    public Map<String, String> validateUserRequest(UserRequestDTO userRequestDTO) {
        Map<String, String> errors = new HashMap<>();


        if (userRequestDTO.getRoleId() == 1L) {
            boolean adminExists = userRepository.existsByRoleId(1L);
            if (adminExists) {
                errors.put("roleId", "An admin user already exists. Only one admin is allowed.");
            }
        }

        if (emailExists(userRequestDTO.getEmail())) {
            errors.put("email", "Email is already in use.");
        }

        if (userRequestDTO.getPhoneNumber() != null && !userRequestDTO.getPhoneNumber().trim().isEmpty()) {
            if (userRequestDTO.getCountryCode() == null || userRequestDTO.getCountryCode().trim().isEmpty()) {
                errors.put("countryCode", "Country code is required when phone number is provided.");
            }
            if (phoneNumberExists(userRequestDTO.getPhoneNumber())) {
                errors.put("phoneNumber", "Phone number is already in use.");
            }
        }

        if (userRequestDTO.getPassword() == null || userRequestDTO.getConfirmPassword() == null ||
                !userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match or are missing");
        }

        if (StringUtils.isNotBlank(userRequestDTO.getDateOfBirth()) &&
                !isValidAge(userRequestDTO.getDateOfBirth())) {
            errors.put("dateOfBirth", "You must be at least 18 years old.");
        }

        return errors;
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmailAndIsActiveTrue(email);
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.existsByPhoneNumberAndIsActiveTrue(phoneNumber);
    }

    private boolean isValidAge(String dateOfBirth) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
            LocalDate today = LocalDate.now();
            int age = Period.between(dob, today).getYears();
            return age >= 18;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public ResponseEntity<Map<String, String>> login(LoginRequestDTO loginRequestDTO) {
        Map<String, String> response = new HashMap<>();

        Users user = userRepository.findByEmailAndIsActiveTrue(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password."));

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(), loginRequestDTO.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                String role = user.getRole().getName();
                List<String> permissions = user.getRole().getPermissions().stream()
                        .map(Permission::getName)
                        .toList();

                String token = jwtService.generateToken(user.getUserId(), role, permissions);

                response.put("jwtToken", token);
                response.put("status", "success");
                response.put("message", "Login successful.");
                return ResponseEntity.ok(response);
            }

        } catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("message", "Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("status", "error");
        response.put("message", "Unexpected error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public UserResponseDTO updateUser(String userId, UserRequestDTO dto) {
        Users user = userRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (!dto.getRoleId().equals(user.getRole().getId())) {
            throw new IllegalArgumentException("You are not authorized to change the user's role.");
        }

        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())) {
            boolean emailExists = emailExists(dto.getEmail());
            if (emailExists) {
                throw new IllegalArgumentException("Email is already in use by another active user.");
            }
        }

        usersMapper.updateUserFromDto(dto, user);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Users updatedUser = userRepository.save(user);
        return usersMapper.toDto(updatedUser);
    }


    public UserResponseDTO softDeleteUser(Long userId) {
        Users user = userRepository.findByIdAndIsActive(userId, true)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.setIsActive(false);
        Users savedUser = userRepository.save(user);
        return usersMapper.toDto(savedUser);
    }

    public UserResponseDTO restoreUser(Long userId) {
        Users user = userRepository.findByIdAndIsActive(userId, false)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId + " or is already active"));

        boolean emailConflict = emailExists(user.getEmail());
        if (emailConflict) {
            throw new IllegalArgumentException("Cannot restore user. Another active user with the same email exists.");
        }

        user.setIsActive(true);
        Users savedUser = userRepository.save(user);
        return usersMapper.toDto(savedUser);
    }

}
