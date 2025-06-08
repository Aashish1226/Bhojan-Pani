package Food.FoodDelivery.project.DTO.ResponseDTO;
import lombok.*;

@Getter
@Setter
public class CityResponseDTO {
    private Long id;
    private String name;
    private String latitude;
    private String longitude;
    private Long stateId;
    private String stateName;
    private Long countryId;
}
