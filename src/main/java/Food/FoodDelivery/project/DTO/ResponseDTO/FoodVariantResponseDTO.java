package Food.FoodDelivery.project.DTO.ResponseDTO;

import Food.FoodDelivery.project.Enum.ServingSize;
import Food.FoodDelivery.project.Enum.ServingUnit;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodVariantResponseDTO {
    private Long id;
    private String label;
    private Double price;
    private String servingSize;
    private String servingUnit;
    private Integer servingQuantity;
    private Boolean isActive;
}
