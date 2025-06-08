package Food.FoodDelivery.project.Mapper;
import Food.FoodDelivery.project.DTO.RequestDTO.StateRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.StateResponseDTO;
import Food.FoodDelivery.project.Entity.State;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface StateMapper {
    @Mapping(source = "country.id" , target = "countryId")
    StateResponseDTO toResponse(State state);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "countryId" , target = "country.id")
    State toEntity(StateRequestDTO stateDto);
}
