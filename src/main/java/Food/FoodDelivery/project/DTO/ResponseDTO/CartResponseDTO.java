package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private Long userId;
    private List<CartItemResponseDTO> items;
}
