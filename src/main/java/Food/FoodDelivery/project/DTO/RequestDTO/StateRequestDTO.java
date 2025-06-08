package Food.FoodDelivery.project.DTO.RequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private Long countryId;
    @NotBlank(message = "state code is required")
    private String stateCode;
    private String latitude;
    private String longitude;
}