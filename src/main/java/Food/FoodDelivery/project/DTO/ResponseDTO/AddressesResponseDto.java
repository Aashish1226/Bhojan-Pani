package Food.FoodDelivery.project.DTO.ResponseDTO;
import Food.FoodDelivery.project.Enum.AddressType;
import lombok.*;

@Getter
@Setter
public class AddressesResponseDto {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String postalCode;
    private String cityName;
    private Long cityId;
    private String stateName;
    private Long stateId;
    private String countryName;
    private Long countryId;
    private String addressType;
}