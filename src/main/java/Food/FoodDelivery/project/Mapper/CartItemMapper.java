package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.CartItemRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.CartItemResponseDTO;
import Food.FoodDelivery.project.Entity.CartItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "food", ignore = true)
    @Mapping(target = "foodVariant" , ignore = true)
    CartItem toEntity(CartItemRequestDTO dto);

    @Mapping(source = "food.id", target = "foodId")
    @Mapping(source = "food.name" , target = "foodName")
    @Mapping(source = "foodVariant.id" , target = "foodVariantId")
    CartItemResponseDTO toDto(CartItem entity);
}
