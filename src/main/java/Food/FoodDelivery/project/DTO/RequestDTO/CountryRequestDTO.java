package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequestDTO {

    @NotBlank(message = "Country name is required.")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters.")
    private String name;
    @Pattern(regexp = "^[0-9]{1,3}$", message = "Phone code must be a numeric value with up to 3 digits.")
    private String phoneCode;
    @NotBlank(message = "iso2 is required")
    private String iso2;
    @NotBlank(message = "iso3 is required")
    private String iso3;
    private String capital;
    private String currency;
    private String currencySymbol;
    private String tld;
    private String nativeName;
    private String emoji;
    private String emojiU;
    private String latitude;
    private String longitude;
    private Integer regionId;
    private Integer subRegionId;
}