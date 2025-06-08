package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<PermissionResponseDTO> permissions;
}
