package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class AssignPermissionsToRoleRequestDTO {
    @NotNull(message = "permissionIds cannot be null")
    private List<Long> permissionIds;
}
