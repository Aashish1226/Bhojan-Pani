package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class TimezoneRequestDTO {

    @NotBlank(message = "Zone name is required.")
    private String zoneName;

    @NotNull(message = "GMT offset is required.")
    private Integer gmtOffset;

    @NotBlank(message = "GMT offset name is required.")
    private String gmtOffsetName;

    @NotBlank(message = "Abbreviation is required.")
    private String abbreviation;

    @NotBlank(message = "Timezone name is required.")
    private String tzName;

    @NotNull(message = "Country ID is required.")
    @Min(value = 1, message = "Country ID must be positive.")
    private Long countryId;
}
