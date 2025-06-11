package Food.FoodDelivery.project.service;
import Food.FoodDelivery.project.DTO.ResponseDTO.CityResponseDTO;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.CityMapper;
import Food.FoodDelivery.project.Repository.StateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final StateRepository stateRepository;
    private final CityMapper cityMapper;

    @Transactional
    public List<CityResponseDTO> getCitiesByStateId(Long stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new CustomEntityNotFoundException("State ID " + stateId + " not found"));
        List<City> cities = state.getCities();
        return cities.stream().map(cityMapper::toResponseDTO).toList();
    }
}
