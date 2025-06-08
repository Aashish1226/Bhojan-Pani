package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class CartRequestDTO {
    @NotNull(message = "items cannot be null")
    @Valid
    private List<CartItemRequestDTO> items;
}
