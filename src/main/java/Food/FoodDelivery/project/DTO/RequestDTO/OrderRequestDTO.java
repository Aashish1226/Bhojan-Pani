package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "Cart Id should not be null")
    private Long cartId;

    @NotNull(message = "Address Id should not be null")
    private Long addressId;
}
