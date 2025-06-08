package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.CountryRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.CountryResponseDTO;
import Food.FoodDelivery.project.Entity.Country;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface CountryMapper {
    @Mapping(source = "emoji", target="flag")
    CountryResponseDTO toResponseDto(Country country);
    @Mapping(source = "name", target = "name")
    Country toEntity(CountryRequestDTO countryRequestDTO);
}