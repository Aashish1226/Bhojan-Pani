package Food.FoodDelivery.project.DTO.ResponseDTO;

import Food.FoodDelivery.project.Enum.FoodType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
    private String categoryName;
    private Boolean isAvailable;
    private String foodType;
    private Boolean hasVariants;
    private Integer totalOrderCount;
    private Integer averagePrepTimeInMinutes;
    private List<FoodVariantResponseDTO> variants;
}
