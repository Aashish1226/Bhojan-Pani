package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfigResponseDTO {
    private Long id;
    private Double taxRate;
    private Double defaultDiscount;
    private Double deliveryCharges;
}
