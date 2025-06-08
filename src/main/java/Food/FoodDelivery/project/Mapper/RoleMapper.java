package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.RoleRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.RoleResponseDTO;
import Food.FoodDelivery.project.Entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { PermissionMapper.class })
public interface RoleMapper {
    Role toEntity(RoleRequestDTO dto);

    @Mapping(target = "permissions", source = "permissions")
    RoleResponseDTO toResponseDto(Role role);
}
