package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequestDTO {

    @NotBlank(message = "Permission name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
}
