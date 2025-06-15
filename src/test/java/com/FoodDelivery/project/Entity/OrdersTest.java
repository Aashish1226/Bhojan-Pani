package com.FoodDelivery.project.Entity;

import Food.FoodDelivery.project.Entity.Orders;
import Food.FoodDelivery.project.Enum.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {

    @Test
    void prePersistShouldSetOrderNumberIfNull() {
        Orders order = Orders.builder().build();

        order.prePersist();

        assertNotNull(order.getOrderNumber());
        assertTrue(order.getOrderNumber().startsWith("ORD-"));
        assertEquals(12, order.getOrderNumber().length());
    }

    @Test
    void prePersistShouldNotOverrideExistingOrderNumber() {
        String existingOrderNumber = "ORD-EXIST123";
        Orders order = Orders.builder()
                .orderNumber(existingOrderNumber)
                .build();

        order.prePersist();

        assertEquals(existingOrderNumber, order.getOrderNumber());
    }

    @Test
    void prePersistShouldSetOrderPlacedAt() {
        Orders order = Orders.builder().build();

        order.prePersist();

        assertNotNull(order.getOrderPlacedAt());
    }

    @Test
    void prePersistShouldNotOverrideExistingOrderPlacedAt() {
        LocalDateTime fixedDate = LocalDateTime.of(2025, 1, 1, 10, 0);
        Orders order = Orders.builder()
                .orderPlacedAt(fixedDate)
                .build();

        order.prePersist();

        assertEquals(fixedDate, order.getOrderPlacedAt());
    }

    @Test
    void testDefaultValues() {
        Orders order = new Orders();
        assertEquals(OrderStatus.PLACED, order.getOrderStatus());
        assertEquals(BigDecimal.ZERO, order.getTotalAmount());
        assertEquals(BigDecimal.ZERO, order.getTax());
        assertEquals(BigDecimal.ZERO, order.getDiscount());
        assertEquals(BigDecimal.ZERO, order.getFinalAmount());
        assertEquals(BigDecimal.ZERO, order.getDeliveryCharges());
        assertEquals(0, order.getPaymentAttempts());
        assertTrue(order.getIsActive());
        assertNotNull(order.getPayments());
        assertTrue(order.getPayments().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        Orders order = new Orders();

        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime now = LocalDateTime.now();

        order.setOrderNumber(orderNumber);
        order.setPaymentAttempts(2);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setTotalAmount(new BigDecimal("500.00"));
        order.setTax(new BigDecimal("50.00"));
        order.setDiscount(new BigDecimal("30.00"));
        order.setFinalAmount(new BigDecimal("520.00"));
        order.setOrderPlacedAt(now);
        order.setOrderDeliveredAt(now.plusDays(1));
        order.setDeliveryCharges(new BigDecimal("40.00"));
        order.setIsActive(false);

        assertEquals(orderNumber, order.getOrderNumber());
        assertEquals(2, order.getPaymentAttempts());
        assertEquals(OrderStatus.DELIVERED, order.getOrderStatus());
        assertEquals(new BigDecimal("500.00"), order.getTotalAmount());
        assertEquals(new BigDecimal("50.00"), order.getTax());
        assertEquals(new BigDecimal("30.00"), order.getDiscount());
        assertEquals(new BigDecimal("520.00"), order.getFinalAmount());
        assertEquals(now, order.getOrderPlacedAt());
        assertEquals(now.plusDays(1), order.getOrderDeliveredAt());
        assertEquals(new BigDecimal("40.00"), order.getDeliveryCharges());
        assertFalse(order.getIsActive());
    }
}
