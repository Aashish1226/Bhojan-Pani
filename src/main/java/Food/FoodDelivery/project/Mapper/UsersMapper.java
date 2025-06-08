package Food.FoodDelivery.project.Mapper;

import Food.FoodDelivery.project.DTO.RequestDTO.UserRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.UserResponseDTO;
import Food.FoodDelivery.project.Entity.Users;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "role" , ignore = true)
    Users toEntity(UserRequestDTO userDto);

    @Mapping(source = "role.name" , target = "roleName")
    UserResponseDTO toDto(Users user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", ignore = true) // Don't overwrite role in the mapper
    void updateUserFromDto(UserRequestDTO dto, @MappingTarget Users user);

}
