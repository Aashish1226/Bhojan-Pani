package com.FoodDelivery.project.DTO.ResponseDTO;

import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Enum.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDTOTest {

    @Test
    void noArgsConstructorShouldInitializeWithNullsAndDefaults() {
        OrderResponseDTO dto = new OrderResponseDTO();

        assertNull(dto.getId());
        assertNull(dto.getCartId());
        assertNull(dto.getOrderNumber());
        assertNull(dto.getOrderStatus());
        assertNull(dto.getDeliveryCharges());
        assertNull(dto.getTotalAmount());
        assertNull(dto.getTax());
        assertNull(dto.getDiscount());
        assertNull(dto.getFinalAmount());
        assertNull(dto.getOrderPlacedAt());
        assertNull(dto.getOrderDeliveredAt());
        assertNull(dto.getDeliveryAddress());
        assertEquals(0, dto.getPaymentAttempts());
    }

    @Test
    void allArgsConstructorShouldSetFieldsCorrectly() {
        LocalDateTime now = LocalDateTime.now();
        AddressesResponseDto addressDto = AddressesResponseDto.builder().id(1L).cityId(12l).build();

        OrderResponseDTO dto = new OrderResponseDTO(
                101L,
                202L,
                "ORD-12345678",
                OrderStatus.PLACED,
                "40.00",
                new BigDecimal("500.00"),
                new BigDecimal("50.00"),
                new BigDecimal("30.00"),
                new BigDecimal("520.00"),
                now,
                now.plusDays(1),
                addressDto,
                2
        );

        assertEquals(101L, dto.getId());
        assertEquals(202L, dto.getCartId());
        assertEquals("ORD-12345678", dto.getOrderNumber());
        assertEquals(OrderStatus.PLACED, dto.getOrderStatus());
        assertEquals("40.00", dto.getDeliveryCharges());
        assertEquals(new BigDecimal("500.00"), dto.getTotalAmount());
        assertEquals(new BigDecimal("50.00"), dto.getTax());
        assertEquals(new BigDecimal("30.00"), dto.getDiscount());
        assertEquals(new BigDecimal("520.00"), dto.getFinalAmount());
        assertEquals(now, dto.getOrderPlacedAt());
        assertEquals(now.plusDays(1), dto.getOrderDeliveredAt());
        assertEquals(addressDto, dto.getDeliveryAddress());
        assertEquals(2, dto.getPaymentAttempts());
    }

    @Test
    void builderShouldBuildCorrectObject() {
        LocalDateTime placedAt = LocalDateTime.now();
        AddressesResponseDto addressDto = AddressesResponseDto.builder().id(2L).cityId(23l).build();

        OrderResponseDTO dto = OrderResponseDTO.builder()
                .id(1L)
                .cartId(2L)
                .orderNumber("ORD-BUILD123")
                .orderStatus(OrderStatus.DELIVERED)
                .deliveryCharges("25.00")
                .totalAmount(new BigDecimal("1000.00"))
                .tax(new BigDecimal("100.00"))
                .discount(new BigDecimal("50.00"))
                .finalAmount(new BigDecimal("1050.00"))
                .orderPlacedAt(placedAt)
                .orderDeliveredAt(placedAt.plusDays(2))
                .deliveryAddress(addressDto)
                .paymentAttempts(3)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getCartId());
        assertEquals("ORD-BUILD123", dto.getOrderNumber());
        assertEquals(OrderStatus.DELIVERED, dto.getOrderStatus());
        assertEquals("25.00", dto.getDeliveryCharges());
        assertEquals(new BigDecimal("1000.00"), dto.getTotalAmount());
        assertEquals(new BigDecimal("100.00"), dto.getTax());
        assertEquals(new BigDecimal("50.00"), dto.getDiscount());
        assertEquals(new BigDecimal("1050.00"), dto.getFinalAmount());
        assertEquals(placedAt, dto.getOrderPlacedAt());
        assertEquals(placedAt.plusDays(2), dto.getOrderDeliveredAt());
        assertEquals(addressDto, dto.getDeliveryAddress());
        assertEquals(3, dto.getPaymentAttempts());
    }

    @Test
    void settersShouldModifyFieldsCorrectly() {
        OrderResponseDTO dto = new OrderResponseDTO();
        LocalDateTime time = LocalDateTime.now();
        AddressesResponseDto addressDto = AddressesResponseDto.builder().id(3L).cityId(23l).build();

        dto.setId(11L);
        dto.setCartId(22L);
        dto.setOrderNumber("ORD-SET123");
        dto.setOrderStatus(OrderStatus.CANCELLED);
        dto.setDeliveryCharges("10.00");
        dto.setTotalAmount(new BigDecimal("250.00"));
        dto.setTax(new BigDecimal("20.00"));
        dto.setDiscount(new BigDecimal("15.00"));
        dto.setFinalAmount(new BigDecimal("255.00"));
        dto.setOrderPlacedAt(time);
        dto.setOrderDeliveredAt(time.plusDays(3));
        dto.setDeliveryAddress(addressDto);
        dto.setPaymentAttempts(1);

        assertEquals(11L, dto.getId());
        assertEquals(22L, dto.getCartId());
        assertEquals("ORD-SET123", dto.getOrderNumber());
        assertEquals(OrderStatus.CANCELLED, dto.getOrderStatus());
        assertEquals("10.00", dto.getDeliveryCharges());
        assertEquals(new BigDecimal("250.00"), dto.getTotalAmount());
        assertEquals(new BigDecimal("20.00"), dto.getTax());
        assertEquals(new BigDecimal("15.00"), dto.getDiscount());
        assertEquals(new BigDecimal("255.00"), dto.getFinalAmount());
        assertEquals(time, dto.getOrderPlacedAt());
        assertEquals(time.plusDays(3), dto.getOrderDeliveredAt());
        assertEquals(addressDto, dto.getDeliveryAddress());
        assertEquals(1, dto.getPaymentAttempts());
    }
}
