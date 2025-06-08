package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfigRequestDTO {

    @NotNull(message = "Tax rate is required")
    private Double taxRate;

    @NotNull(message = "Default discount is required")
    private Double defaultDiscount;

}
