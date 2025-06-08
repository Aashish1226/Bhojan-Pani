package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.RegionRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.RegionResponseDto;
import org.mapstruct.Mapper;
import Food.FoodDelivery.project.Entity.Region;

@Mapper(componentModel = "spring", uses = {SubRegionMapper.class})
public interface RegionMapper {
    Region toEntity(RegionRequestDto dto);
    RegionResponseDto toDto(Region region);
}
