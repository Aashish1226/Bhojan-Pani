package Food.FoodDelivery.project.Controller;
import Food.FoodDelivery.project.DTO.RequestDTO.StateRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/states")
public class StateController {
    private final StateService stateService;
    private final CityService cityService;

    public StateController(StateService stateService, CityService cityService) {
        this.stateService = stateService;
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<List<StateResponseDTO>> createStates(@RequestBody List<StateRequestDTO> stateRequestDTOs) {
        List<StateResponseDTO> states = stateService.createStates(stateRequestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(states);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<?> getStatesByCountryId(@PathVariable Long countryId) {
        List<StateResponseDTO> states = stateService.getStatesByCountryId(countryId);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/cities/{stateId}")
    public ResponseEntity<List<CityResponseDTO>> getCitiesByState(@PathVariable Long stateId) {
        List<CityResponseDTO> cities = cityService.getCitiesByStateId(stateId);
        return ResponseEntity.ok(cities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
        return ResponseEntity.ok(CommonResponse.success("State deleted successfully"));
    }
}
