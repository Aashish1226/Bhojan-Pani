package Food.FoodDelivery.project.DTO.RequestDTO;

import Food.FoodDelivery.project.Enum.*;
import Food.FoodDelivery.project.validation.EnumValidator;
import Food.FoodDelivery.project.validation.ValidServingFields;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidServingFields
public class FoodVariantRequestDTO {

    @NotBlank(message = "Label is required")
    private String label;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @EnumValidator(enumClass = ServingSize.class, message = "Invalid serving size. Allowed values: SMALL, MEDIUM, LARGE, etc.")
    private String servingSize;

    @EnumValidator(enumClass = ServingUnit.class, message = "Invalid serving unit. Allowed values: GRAM, ML, PIECE, etc.")
    private String servingUnit;

    @Positive(message = "Serving quantity must be positive")
    private Integer servingQuantity;

}
