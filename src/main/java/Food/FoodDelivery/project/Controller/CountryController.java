package Food.FoodDelivery.project.Controller;
import Food.FoodDelivery.project.DTO.RequestDTO.CountryRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Entity.Country;
import Food.FoodDelivery.project.service.CountryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;
    private final ObjectMapper objectMapper;

    public CountryController(CountryService countryService, ObjectMapper objectMapper) {
        this.countryService = countryService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importAllCountries(@RequestParam("file") MultipartFile file) {
        try {
            List<Country> countries = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Country>>() {});
            countryService.importAsync(countries);
            return ResponseEntity.ok("All data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid file format or unable to process the file");
        }
    }

    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {
        List<CountryResponseDTO> countries = countryService.getAllCountries();
        if (countries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(countries);
        }
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable Long id) {
        CountryResponseDTO country = countryService.getCountryById(id);
        if (country == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(country);
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<CountryResponseDTO>> getCountriesByRegionId(@PathVariable Long regionId){
        List<CountryResponseDTO> courntryList = countryService.getAllCountriesByRegionId(regionId);
        return ResponseEntity.ok(courntryList);
    }

    @GetMapping("/timeZones/{countryId}")
    public ResponseEntity<List<TimezoneResponseDTO>> getTimezonesByCountry(@PathVariable Long countryId) {
        List<TimezoneResponseDTO> timezones = countryService.getTimezonesByCountryId(countryId);
        return ResponseEntity.ok(timezones);
    }

}