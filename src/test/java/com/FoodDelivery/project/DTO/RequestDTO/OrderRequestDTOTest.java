package com.FoodDelivery.project.DTO.RequestDTO;

import Food.FoodDelivery.project.DTO.RequestDTO.OrderRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void noArgsConstructorShouldHaveNullFields() {
        OrderRequestDTO dto = new OrderRequestDTO();
        assertNull(dto.getCartId());
        assertNull(dto.getAddressId());
    }

    @Test
    void allArgsConstructorShouldSetFieldsCorrectly() {
        OrderRequestDTO dto = new OrderRequestDTO(101L, 202L);
        assertEquals(101L, dto.getCartId());
        assertEquals(202L, dto.getAddressId());
    }

    @Test
    void settersAndGettersShouldWorkCorrectly() {
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setCartId(1L);
        dto.setAddressId(2L);

        assertEquals(1L, dto.getCartId());
        assertEquals(2L, dto.getAddressId());
    }

    @Test
    void shouldFailValidationWhenCartIdIsNull() {
        OrderRequestDTO dto = new OrderRequestDTO(null, 123L);
        Set<ConstraintViolation<OrderRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cartId")));
    }

    @Test
    void shouldFailValidationWhenAddressIdIsNull() {
        OrderRequestDTO dto = new OrderRequestDTO(456L, null);
        Set<ConstraintViolation<OrderRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("addressId")));
    }

    @Test
    void shouldFailValidationWhenBothFieldsAreNull() {
        OrderRequestDTO dto = new OrderRequestDTO(null, null);
        Set<ConstraintViolation<OrderRequestDTO>> violations = validator.validate(dto);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cartId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("addressId")));
    }

    @Test
    void shouldPassValidationWhenAllFieldsArePresent() {
        OrderRequestDTO dto = new OrderRequestDTO(10L, 20L);
        Set<ConstraintViolation<OrderRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }
}
