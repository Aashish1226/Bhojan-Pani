package Food.FoodDelivery.project.DTO.RequestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long cartId;
    private Long addressId;
}
