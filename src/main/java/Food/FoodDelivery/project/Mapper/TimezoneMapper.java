package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.TimezoneRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.TimezoneResponseDTO;
import Food.FoodDelivery.project.Entity.Timezone;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TimezoneMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "country", ignore = true)
    Timezone toEntity(TimezoneRequestDTO dto);

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    TimezoneResponseDTO toResponseDTO(Timezone timezone);

}
