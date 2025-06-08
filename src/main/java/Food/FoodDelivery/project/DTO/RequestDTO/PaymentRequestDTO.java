package Food.FoodDelivery.project.DTO.RequestDTO;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    private BigDecimal amount;
    private String currency;
    private Long orderId;
}
