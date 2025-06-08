package Food.FoodDelivery.project.Mapper;

import Food.FoodDelivery.project.DTO.RequestDTO.OrderConfigRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.OrderConfigResponseDTO;
import Food.FoodDelivery.project.Entity.OrderConfig;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderConfigMapper {

    OrderConfig toEntity(OrderConfigRequestDTO dto);

    OrderConfigResponseDTO toDto(OrderConfig orderConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderConfigFromDto(OrderConfigRequestDTO dto, @MappingTarget OrderConfig entity);
}
