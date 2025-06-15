package com.FoodDelivery.project.DTO.RequestDTO;

import Food.FoodDelivery.project.DTO.RequestDTO.UserRequestDTO;
import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserRequestDTO() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Aashish");
        dto.setMiddleName("Kumar");
        dto.setLastName("Saini");
        dto.setDateOfBirth("1998-11-20");
        dto.setEmail("aashish.saini@gmail.com");
        dto.setCountryCode("+91");
        dto.setPhoneNumber("9876543210");
        dto.setPassword("India@123");
        dto.setConfirmPassword("India@123");
        dto.setRoleId(1L);

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO should be valid");
    }

    @Test
    void testFirstNameTrimAndValidation() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("   Aditya   ");
        assertEquals("Aditya", dto.getFirstName());

        dto.setFirstName("   ");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "First name with only spaces should fail validation");
    }

    @Test
    void testInvalidEmailFormat() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("adityaemailwrongformat");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Invalid email format")));
    }

    @Test
    void testPhoneNumberValidation() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setPhoneNumber("12345");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testPasswordValidation() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setPassword("onlylowercase");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNullRoleIdFails() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setRoleId(null);
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Role id cannot be null")));
    }

    @Test
    void testConfirmPasswordIsRequired() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setConfirmPassword("");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testSetterTrimsCorrectly() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("  Aashish  ");
        dto.setMiddleName("  Kumar  ");
        dto.setLastName("  Saini ");
        dto.setEmail("   aashish@example.in  ");
        dto.setPhoneNumber(" 9876543210 ");

        assertEquals("Aashish", dto.getFirstName());
        assertEquals("Kumar", dto.getMiddleName());
        assertEquals("Saini", dto.getLastName());
        assertEquals("aashish@example.in", dto.getEmail());
        assertEquals("9876543210", dto.getPhoneNumber());
    }
}
