package Food.FoodDelivery.project.Mapper;

import Food.FoodDelivery.project.DTO.RequestDTO.OrderRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.OrderResponseDTO;
import Food.FoodDelivery.project.Entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {AddressesMapper.class})
public interface OrderMapper {

    @Mapping(target = "cart" , ignore = true)
    @Mapping(target = "deliveryAddress" , ignore = true)
    Orders toEntity(OrderRequestDTO orderRequestDTO);

    @Mapping(source = "cart.id" , target = "cartId")
    @Mapping(source = "deliveryAddress" , target = "deliveryAddress")
    OrderResponseDTO toResponseDTO(Orders orders);
}
