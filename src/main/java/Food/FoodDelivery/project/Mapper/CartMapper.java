package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.*;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    @Mapping(target = "user", ignore = true)
    Cart toEntity(CartRequestDTO dto);

    @Mapping(source = "user.id", target = "userId")
    CartResponseDTO toDto(Cart entity);

    List<CartItem> toCartItemEntities(List<CartItemRequestDTO> dtoList);

    List<CartItemResponseDTO> toCartItemDtos(List<CartItem> entityList);
}
