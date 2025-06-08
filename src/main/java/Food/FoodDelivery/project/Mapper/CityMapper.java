package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.CityRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.CityResponseDTO;
import Food.FoodDelivery.project.Entity.City;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "state.country.id", target = "countryId")
    CityResponseDTO toResponseDTO(City city);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "state", ignore = true)
    City toEntity(CityRequestDTO dto);
}
