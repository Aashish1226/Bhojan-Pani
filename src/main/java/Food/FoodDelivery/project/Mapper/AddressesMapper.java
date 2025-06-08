package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.AddressesRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.AddressesResponseDto;
import Food.FoodDelivery.project.Entity.Addresses;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressesMapper {

    @Mapping(target = "city", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "country", ignore = true)
    Addresses toEntity(AddressesRequestDto requestDto);

    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.id", target = "countryId")
    AddressesResponseDto toResponse(Addresses entity);

    List<AddressesResponseDto> toResponseList(List<Addresses> addresses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AddressesRequestDto dto, @MappingTarget Addresses entity);
}