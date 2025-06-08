package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class CartItemRequestDTO {
    @NotNull(message = "Food id cannot be null")
    private Long foodId;
    private Long foodVariantId;
}
