package Food.FoodDelivery.project.DTO.RequestDTO;
import Food.FoodDelivery.project.Enum.FoodType;
import Food.FoodDelivery.project.validation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidFoodRequest
public class FoodRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Category must be specified")
    private Long categoryId;

    @NotNull(message = "Availability must be specified")
    private Boolean isAvailable;

    @NotNull(message = "Food type must be specified")
    @EnumValidator(enumClass = FoodType.class, message = "Invalid food type. Allowed values: VEG, NON_VEG, VEGAN, etc.")
    private String foodType;

    @NotNull(message = "hasVariants must be specified")
    private Boolean hasVariants;

    @NotNull(message = "Average preperations time cannot be null")
    private Integer averagePrepTimeInMinutes;

    @Valid
    private List<FoodVariantRequestDTO> variants;
}
