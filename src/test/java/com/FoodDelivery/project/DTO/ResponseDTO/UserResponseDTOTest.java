package com.FoodDelivery.project.DTO.ResponseDTO;

import Food.FoodDelivery.project.DTO.ResponseDTO.UserResponseDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDTOTest {

    @Test
    void testConstructorWithAllFields() {
        UserResponseDTO user = new UserResponseDTO(
                "Aashish",
                "Kumar",
                "Saini",
                "1998-10-10",
                "+91",
                "9876543210",
                "aashish.saini@gmail.com",
                "USER"
        );

        assertEquals("Aashish", user.getFirstName());
        assertEquals("Kumar", user.getMiddleName());
        assertEquals("Saini", user.getLastName());
        assertEquals("1998-10-10", user.getDateOfBirth());
        assertEquals("+91", user.getCountryCode());
        assertEquals("9876543210", user.getPhoneNumber());
        assertEquals("aashish.saini@gmail.com", user.getEmail());
        assertEquals("USER", user.getRoleName());
    }

    @Test
    void testSetterMethodsIndividually() {
        UserResponseDTO user = new UserResponseDTO();

        user.setFirstName("Aditya");
        user.setMiddleName("Narayan");
        user.setLastName("Sharma");
        user.setDateOfBirth("1996-01-26");
        user.setCountryCode("+91");
        user.setPhoneNumber("9123456789");
        user.setEmail("aditya.sharma@example.in");
        user.setRoleName("ADMIN");

        assertEquals("Aditya", user.getFirstName());
        assertEquals("Narayan", user.getMiddleName());
        assertEquals("Sharma", user.getLastName());
        assertEquals("1996-01-26", user.getDateOfBirth());
        assertEquals("+91", user.getCountryCode());
        assertEquals("9123456789", user.getPhoneNumber());
        assertEquals("aditya.sharma@example.in", user.getEmail());
        assertEquals("ADMIN", user.getRoleName());
    }
}
