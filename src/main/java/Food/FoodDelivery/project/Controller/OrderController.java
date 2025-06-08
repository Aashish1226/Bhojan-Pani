package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.OrderRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.OrderResponseDTO;
import Food.FoodDelivery.project.Enum.*;
import Food.FoodDelivery.project.service.OrderService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO, @RequestAttribute("userUUID") String userUUID) {
        OrderResponseDTO orderResponse = orderService.placeOrder(userUUID, orderRequestDTO.getCartId(), orderRequestDTO.getAddressId());
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(required = false) @DecimalMin(value = "0.0", message = "Min amount cannot be negative") BigDecimal minAmount,
            @RequestParam(required = false) @DecimalMin(value = "0.0", message = "Max Amount cannot be negative") BigDecimal maxAmount,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "orderPlacedAt") String sortBy,
            @RequestParam(defaultValue = "desc") @Pattern(regexp = "^(asc|desc)$") String direction
    ) {
        if (orderStatus != null && Arrays.stream(OrderStatus.values()).noneMatch(e -> e.name().equalsIgnoreCase(orderStatus))) {
            throw new IllegalArgumentException("Invalid order status. Allowed values are: " + Arrays.toString(OrderStatus.values()));
        }

        if (!OrderSortField.isValid(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy + ". Valid fields are: " + Arrays.toString(OrderSortField.values()));
        }
        Page<OrderResponseDTO> orders = orderService.getOrders(orderStatus, isActive, fromDate, toDate, minAmount, maxAmount, page, size, sortBy, direction);

        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam("status") String newStatus) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }

}
