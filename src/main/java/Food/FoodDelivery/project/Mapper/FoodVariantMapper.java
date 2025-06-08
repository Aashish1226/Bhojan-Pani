package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.FoodVariantRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.FoodVariantResponseDTO;
import Food.FoodDelivery.project.Entity.FoodVariant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodVariantMapper {

     FoodVariant toEntity(FoodVariantRequestDTO dto);

    FoodVariantResponseDTO toDto(FoodVariant entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(FoodVariantRequestDTO dto, @MappingTarget FoodVariant entity);


    List<FoodVariant> toEntityList(List<FoodVariantRequestDTO> dtoList);

    List<FoodVariantResponseDTO> toDtoList(List<FoodVariant> entityList);
}
