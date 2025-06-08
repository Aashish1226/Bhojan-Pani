package Food.FoodDelivery.project.DTO.ResponseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateResponseDTO {
    private Long id;
    private String name;
    private Long countryId;
    private String stateCode;
    private String latitude;
    private String longitude;
}