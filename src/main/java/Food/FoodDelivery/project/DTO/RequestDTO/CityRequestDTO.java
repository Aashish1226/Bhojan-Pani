package Food.FoodDelivery.project.DTO.RequestDTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class CityRequestDTO {

    @NotBlank(message = "City name is required.")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters.")
    private String name;

    @Size(max = 50, message = "Latitude must not exceed 50 characters.")
    private String latitude;

    @Size(max = 50, message = "Longitude must not exceed 50 characters.")
    private String longitude;

    @NotNull(message = "State ID is required.")
    @Min(value = 1, message = "State ID must be a positive number.")
    private Long stateId;

    @NotNull(message = "Country ID is required.")
    @Min(value = 1, message = "Country ID must be a positive number.")
    private Long countryId;

}
