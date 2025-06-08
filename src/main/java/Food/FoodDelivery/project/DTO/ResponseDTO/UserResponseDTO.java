package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private String countryCode;
    private String phoneNumber;
    private String email;
    private String roleName;
}
