package com.FoodDelivery.project.Entity;

import Food.FoodDelivery.project.Entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    private Users user;

    @BeforeEach
    void setup() {
        user = new Users();
    }

    @Test
    void testUserCreation() {
        Users newUser = new Users();

        assertNotNull(newUser);
        assertTrue(newUser.getIsActive());
        assertNull(newUser.getId());
        assertNull(newUser.getUserId());
    }

    @Test
    void testGetterSetters() {
        user.setId(1L);
        user.setUserId("user-123");
        user.setFirstName("Ram");
        user.setMiddleName("Kumar");
        user.setLastName("Sharma");
        user.setEmail("RamSharma@email.com");
        user.setPhoneNumber("9876543210");
        user.setPassword("password123");
        user.setCountryCode("+91");
        user.setDateOfBirth(LocalDate.of(1990, 5, 15));

        assertEquals(1L, user.getId());
        assertEquals("user-123", user.getUserId());
        assertEquals("Ram", user.getFirstName());
        assertEquals("Kumar", user.getMiddleName());
        assertEquals("Sharma", user.getLastName());
        assertEquals("RamSharma@email.com", user.getEmail());
        assertEquals("9876543210", user.getPhoneNumber());
        assertEquals("password123", user.getPassword());
        assertEquals("+91", user.getCountryCode());
        assertEquals(LocalDate.of(1990, 5, 15), user.getDateOfBirth());
    }

    @Test
    void testActiveStatus() {
        assertTrue(user.getIsActive());

        user.setIsActive(false);
        assertFalse(user.getIsActive());

        user.setIsActive(true);
        assertTrue(user.getIsActive());
    }

    @Test
    void testRoleAssignment() {
        Role userRole = new Role();
        user.setRole(userRole);
        assertEquals(userRole, user.getRole());
    }

    @Test
    void testUUIDGeneration() {
        user.setUserId(null);
        user.generateUUID();

        assertNotNull(user.getUserId());
        assertDoesNotThrow(() -> UUID.fromString(user.getUserId()));
    }

    @Test
    void testExistingUUIDNotOverwritten() {
        String existingId = "Custom-ID";
        user.setUserId(existingId);
        user.generateUUID();

        assertEquals(existingId, user.getUserId());
    }

    @Test
    void testDateOfBirth() {
        LocalDate birthDate = LocalDate.of(1995, 12, 25);
        user.setDateOfBirth(birthDate);

        assertEquals(birthDate, user.getDateOfBirth());
        assertEquals(1995, user.getDateOfBirth().getYear());
        assertEquals(12, user.getDateOfBirth().getMonthValue());
        assertEquals(25, user.getDateOfBirth().getDayOfMonth());
    }

    @Test
    void testOptionalFields() {
        user.setMiddleName(null);
        assertNull(user.getMiddleName());
        user.setDateOfBirth(null);
        assertNull(user.getDateOfBirth());
    }

    @Test
    void testTimestampFields() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusMinutes(30);

        user.setCreateDate(now);
        user.setUpdateDate(later);

        assertEquals(now, user.getCreateDate());
        assertEquals(later, user.getUpdateDate());
        assertTrue(user.getUpdateDate().isAfter(user.getCreateDate()));
    }

    @Test
    void testMultipleUsersIndependence() {
        Users user1 = new Users();
        Users user2 = new Users();

        user1.setFirstName("Alice");
        user2.setFirstName("Bob");

        user1.generateUUID();
        user2.generateUUID();

        assertNotEquals(user1.getFirstName(), user2.getFirstName());
        assertNotEquals(user1.getUserId(), user2.getUserId());
    }

    @Test
    void testEmailValidation() {
        String email = "test@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testPhoneNumberHandling() {
        String phoneNumber = "9965658261";
        user.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    void testPasswordStorage() {
        String password = "mySecretPassword";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void testUserWithAllFields() {
        user.setFirstName("Raj");
        user.setLastName("Sharma");
        user.setEmail("raj.sharma@gmail.com");
        user.setPhoneNumber("9123456789");
        user.setPassword("securePass123");
        user.setCountryCode("+91");
        user.setDateOfBirth(LocalDate.of(1988, 3, 20));
        user.generateUUID();

        assertEquals("Raj", user.getFirstName());
        assertEquals("Sharma", user.getLastName());
        assertEquals("raj.sharma@gmail.com", user.getEmail());
        assertEquals("9123456789", user.getPhoneNumber());
        assertEquals("securePass123", user.getPassword());
        assertEquals("+91", user.getCountryCode());
        assertNotNull(user.getUserId());
        assertTrue(user.getIsActive());
    }

    @Test
    void testEmptyMiddleName() {
        user.setFirstName("Honey");
        user.setLastName("Singh");

        assertEquals("Honey", user.getFirstName());
        assertEquals("Singh", user.getLastName());
        assertNull(user.getMiddleName());
    }
}