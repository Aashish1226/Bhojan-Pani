package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.FoodRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.FoodResponseDTO;
import Food.FoodDelivery.project.Entity.Food;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FoodVariantMapper.class})
public interface FoodMapper {

    @Mapping(target = "category", ignore = true)
    Food toEntity(FoodRequestDTO dto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name" , target = "categoryName")
    FoodResponseDTO toDto(Food food);

    List<FoodResponseDTO> toDtoList(List<Food> foodList);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateEntityFromDto(FoodRequestDTO dto, @MappingTarget Food entity);
}
