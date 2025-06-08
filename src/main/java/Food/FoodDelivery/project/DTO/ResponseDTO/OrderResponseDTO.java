package Food.FoodDelivery.project.DTO.ResponseDTO;

import Food.FoodDelivery.project.Entity.Addresses;
import Food.FoodDelivery.project.Enum.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long cartId;
    private String orderNumber;
    private OrderStatus orderStatus;
    private String deliveryCharges;
    private BigDecimal totalAmount;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    private LocalDateTime orderPlacedAt;
    private LocalDateTime orderDeliveredAt;
    private AddressesResponseDto deliveryAddress;
    private int paymentAttempts;
}
