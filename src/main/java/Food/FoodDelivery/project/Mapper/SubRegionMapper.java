package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.SubRegionRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.SubRegionResponseDto;
import Food.FoodDelivery.project.Entity.SubRegion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubRegionMapper {
    SubRegion toEntity(SubRegionRequestDto dto);
    SubRegionResponseDto toDto(SubRegion entity);
}
