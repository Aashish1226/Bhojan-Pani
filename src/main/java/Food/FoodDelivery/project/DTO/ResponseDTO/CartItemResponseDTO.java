package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDTO {
    private Long id;
    private String foodId;
    private String foodName;
    private Integer quantity;
    private LocalDateTime createDate;
    private Long foodVariantId;
}
