package Food.FoodDelivery.project.service;
import Food.FoodDelivery.project.DTO.RequestDTO.StateRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.StateResponseDTO;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.StateMapper;
import Food.FoodDelivery.project.Repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final StateMapper stateMapper;

    public StateService(StateRepository stateRepository, CountryRepository countryRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
        this.stateMapper = stateMapper;
    }

    public List<StateResponseDTO> createStates(List<StateRequestDTO> stateRequestDTOs) {
        if (stateRequestDTOs == null || stateRequestDTOs.isEmpty()) {
            throw new IllegalArgumentException("StateRequestDTO list cannot be null or empty");
        }
        Set<String> uniqueStatesInRequest = new HashSet<>();
        for (StateRequestDTO dto : stateRequestDTOs) {
            String key = dto.getName().trim().toLowerCase() + "_" + dto.getCountryId();
            if (!uniqueStatesInRequest.add(key)) {
                throw new IllegalArgumentException("Duplicate state in request: " + dto.getName());
            }
        }
        List<State> states = stateRequestDTOs.stream().map(dto -> {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new CustomEntityNotFoundException("Country not found with ID: " + dto.getCountryId()));
            if (stateRepository.existsByNameAndCountryId(dto.getName().trim(), dto.getCountryId())) {
                throw new IllegalArgumentException("State already exists with name: " + dto.getName()
                        + " for Country ID: " + dto.getCountryId());
            }
            State state = new State();
            state.setName(dto.getName().trim());
            state.setCountry(country);
            return state;
        }).collect(Collectors.toList());
        List<State> savedStates = stateRepository.saveAll(states);
        return savedStates.stream().map(stateMapper::toResponse).toList();
    }

    public List<StateResponseDTO> getStatesByCountryId(Long countryId) {
        if (countryId == null || countryId <= 0) {
            throw new IllegalArgumentException("Invalid Country ID provided");
        }
        List<State> states = stateRepository.findByCountryIdOrderByNameAsc(countryId);
        return states.stream().map(stateMapper::toResponse).toList();
    }


    public void deleteState(Long stateId) {
        if (stateId == null || stateId <= 0) {
            throw new IllegalArgumentException("Invalid State ID provided");
        }
        State existingState = stateRepository.findById(stateId).orElseThrow(() -> new CustomEntityNotFoundException("State not found with ID: " + stateId));
        stateRepository.delete(existingState);
    }
}