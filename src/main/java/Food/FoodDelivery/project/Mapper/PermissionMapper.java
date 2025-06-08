package Food.FoodDelivery.project.Mapper;

import Food.FoodDelivery.project.DTO.RequestDTO.PermissionRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.PermissionResponseDTO;
import Food.FoodDelivery.project.Entity.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

   Permission toEntity(PermissionRequestDTO dto);

    PermissionResponseDTO toResponseDto(Permission entity);

    List<PermissionResponseDTO> toResponseList(List<Permission> permissions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermissionFromDto(PermissionRequestDTO dto, @MappingTarget Permission entity);
}
