package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.ResponseDTO.OrderResponseDTO;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Enum.OrderStatus;
import Food.FoodDelivery.project.Mapper.OrderMapper;
import Food.FoodDelivery.project.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository usersRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final OrderMapper orderMapper;
    private final AddressRepository addressRepository;
    private final OrderConfigRepository orderConfigRepository;

    @Transactional
    public OrderResponseDTO placeOrder(String userUuid, Long cartId, Long addressId) {
        Users user = usersRepository.findByUserIdAndIsActiveTrue(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + userUuid));

        Cart cart = cartRepository.findByIdAndIsActiveTrue(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        boolean hasSuccessfulOrder = orderRepository.existsByCartIdAndOrderStatus(cartId, OrderStatus.CONFIRMED);

        if (hasSuccessfulOrder) {
            throw new RuntimeException("This cart already has a successfully confirmed order. Please create a new cart.");
        }


        if (!Objects.equals(cart.getUser().getId(), user.getId())) {
            throw new AccessDeniedException("You cannot place order for a cart not owned by you.");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot place order for empty cart");
        }

        Addresses deliveryAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        if (!Objects.equals(deliveryAddress.getUser().getId(), user.getId())) {
            throw new AccessDeniedException("Address does not belong to the user.");
        }

        List<OrderConfig> orderConfigActiveList = orderConfigRepository.findByIsActive(true);
        OrderConfig orderConfig = orderConfigActiveList.getFirst();

        double totalAmount = 0.0;
        for (CartItem item : cart.getItems()) {
            Food food = item.getFood();
            food.setTotalOrderCount(food.getTotalOrderCount() + item.getQuantity());
            foodRepository.save(food);

            totalAmount += food.getPrice() * item.getQuantity();
        }

        double tax = totalAmount * (orderConfig.getTaxRate() / 100.0);
        double discount = orderConfig.getDefaultDiscount();
        BigDecimal deliveryCharges = orderConfig.getDeliveryCharges() != null ? orderConfig.getDeliveryCharges() : BigDecimal.ZERO;
        double finalAmount = (totalAmount + tax + deliveryCharges.doubleValue()) - discount;


        Orders orders = Orders.builder()
                .cart(cart)
                .orderStatus(OrderStatus.PLACED)
                .totalAmount(BigDecimal.valueOf(totalAmount))
                .tax(BigDecimal.valueOf(tax))
                .deliveryCharges(deliveryCharges)
                .discount(BigDecimal.valueOf(discount))
                .finalAmount(BigDecimal.valueOf(finalAmount))
                .deliveryAddress(deliveryAddress)
                .isActive(true)
                .build();

        orderRepository.save(orders);
        return orderMapper.toResponseDTO(orders);
    }

    public Page<OrderResponseDTO> getOrders(String orderStatus, Boolean isActive, LocalDateTime fromDate, LocalDateTime toDate, BigDecimal minAmount, BigDecimal maxAmount, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        OrderStatus status = parseOrderStatus(orderStatus);

        Page<Orders> orders = orderRepository.findByFilters(
                status, isActive, fromDate, toDate, minAmount, maxAmount, pageable
        );

        return orders.map(orderMapper::toResponseDTO);
    }

    private OrderStatus parseOrderStatus(String orderStatus) {
        if (orderStatus == null || orderStatus.isBlank()) {
            return null;
        }
        try {
            return OrderStatus.valueOf(orderStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + orderStatus);
        }
    }

    public OrderResponseDTO updateOrderStatus(Long orderId, String newStatus) {
        Orders orders = orderRepository.findByIdAndIsActive(orderId, true)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));

        OrderStatus status;
        try {
            status = OrderStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + newStatus + ". Valid statuses are: " + Arrays.toString(OrderStatus.values()));
        }

        orders.setOrderStatus(status);

        if (status == OrderStatus.DELIVERED) {
            if (orders.getOrderDeliveredAt() == null) {
                orders.setOrderDeliveredAt(LocalDateTime.now());
            }
            orders.setIsActive(false);
        } else if (status == OrderStatus.CANCELLED) {
            orders.setIsActive(false);
        }
        Orders updatedOrders = orderRepository.save(orders);
        return orderMapper.toResponseDTO(updatedOrders);
    }

}
