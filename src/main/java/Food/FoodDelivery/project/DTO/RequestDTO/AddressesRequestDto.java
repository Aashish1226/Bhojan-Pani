package Food.FoodDelivery.project.DTO.RequestDTO;

import Food.FoodDelivery.project.Enum.*;
import Food.FoodDelivery.project.validation.EnumValidator;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressesRequestDto {

    @NotNull(message = "Address Line 1 cannot be null")
    @Size(max = 255, message = "Address Line 1 must not exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address Line 2 must not exceed 255 characters")
    private String addressLine2;

    @Size(max = 255, message = "Landmark must not exceed 255 characters")
    private String landmark;

    @NotNull(message = "Postal code is required")
    @Pattern(regexp = "^[0-9A-Za-z\\-\\s]{3,15}$", message = "Invalid postal code format")
    private String postalCode;

    @NotNull(message = "City Id cannot be null")
    private Long cityId;

    @NotNull(message = "State Id cannot be null")
    private Long stateId;

    @NotNull(message = "Country ID cannot be null")
    private Long countryId;

    @NotNull(message = "Address type is required")
    @EnumValidator(enumClass = AddressType.class)
    private String addressType;
}
