package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private Long userId;
    private LocalDateTime createDate;
    private List<CartItemResponseDTO> items;
}
