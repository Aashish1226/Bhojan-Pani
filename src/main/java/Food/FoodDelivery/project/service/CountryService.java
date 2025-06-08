package Food.FoodDelivery.project.service;
import Food.FoodDelivery.project.DTO.RequestDTO.CountryRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Mapper.*;
import Food.FoodDelivery.project.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
   private final TimezoneMapper timezoneMapper;
    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper, RegionRepository regionRepository, TimezoneMapper timezoneMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
        this.regionRepository = regionRepository;
        this.timezoneMapper = timezoneMapper;
    }
    public List<CountryResponseDTO> createCountries(List<CountryRequestDTO> countryRequestDTOList) {
        Set<String> isoCodes = new HashSet<>();
        Set<String> names = new HashSet<>();
        Set<String> phonecodes = new HashSet<>();
        for (CountryRequestDTO dto : countryRequestDTOList) {
            if (!isoCodes.add(dto.getIso2())) {
                throw new IllegalArgumentException("Duplicate ISO 2 Code in request: " + dto.getIso2());
            }
            if (!names.add(dto.getName())) {
                throw new IllegalArgumentException("Duplicate country name in request: " + dto.getName());
            }
            if (!phonecodes.add(dto.getPhoneCode())) {
                throw new IllegalArgumentException("Duplicate phone code in request: " + dto.getPhoneCode());
            }
        }
        for (CountryRequestDTO dto : countryRequestDTOList) {
            String phoneCodeValue = dto.getPhoneCode();
            if (countryRepository.existsByIso2((dto.getIso2()))){
                throw new IllegalArgumentException("Country already exists with ISO 2 Code: " + dto.getIso2());
            }
            if (countryRepository.existsByName(dto.getName())) {
                throw new IllegalArgumentException("Country already exists with name: " + dto.getName());
            }
            if (countryRepository.existsByPhoneCode(phoneCodeValue)){
                throw new IllegalArgumentException("Country already exists with phone code: " + dto.getPhoneCode());
            }
        }
        List<Country> countries = countryRequestDTOList.stream().map(countryMapper::toEntity).collect(Collectors.toList());
        List<Country> savedCountries = countryRepository.saveAll(countries);
        return savedCountries.stream().map(countryMapper::toResponseDto).collect(Collectors.toList());
    }

    public List<CountryResponseDTO> getAllCountries() {
        List<String> priorityNames = List.of("India", "Singapore" , "United States", "United Arab Emirates");

        List<Country> priorityCountries = countryRepository.findByNameInIgnoreCase(priorityNames);
        List<CountryResponseDTO> prioritized = priorityCountries.stream()
                .sorted(Comparator.comparing(c -> priorityNames.indexOf(c.getName())))
                .map(countryMapper::toResponseDto)
                .toList();

        List<Country> otherCountries = countryRepository.findAllExcludingNames(priorityNames);
        List<CountryResponseDTO> others = otherCountries.stream()
                .map(countryMapper::toResponseDto)
                .toList();

        List<CountryResponseDTO> finalList = new ArrayList<>();
        finalList.addAll(prioritized);
        finalList.addAll(others);
        return finalList;

    }
    public CountryResponseDTO getCountryById(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Country not found with ID: " + id));
        return countryMapper.toResponseDto(country);
    }

    @Async
    public void importAsync(List<Country> countries){
        importCountriesInBatches(countries);
    }

    @Transactional
    public void importCountriesInBatches(List<Country> countries) {
        final int batchSize = 50;
        List<Country> batch = new ArrayList<>(batchSize);

        for (Country country : countries) {
            country.getStates().forEach(state -> {
                state.setCountry(country);
                state.getCities().forEach(city -> city.setState(state));
            });
            country.getTimezones().forEach(tz -> tz.setCountry(country));
            batch.add(country);
            if (batch.size() == batchSize) {
                saveBatch(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            saveBatch(batch);
        }

        System.out.println(("Import complete. Total countries imported: " + countries.size()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBatch(List<Country> countries) {
        countryRepository.saveAll(countries);
        countryRepository.flush();
    }

    public List<CountryResponseDTO> getAllCountriesByRegionId(Long regionId){

        regionRepository.findById(regionId).orElseThrow(() -> new EntityNotFoundException("Region do not exists with id : " + regionId));

        List<Country> allCountries = countryRepository.findByRegionId(regionId);

        return allCountries.stream().map(countryMapper::toResponseDto).toList();

    }

    public List<TimezoneResponseDTO> getTimezonesByCountryId(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new IllegalArgumentException("Country with ID " + countryId + " not found"));
        List<Timezone> timezones = country.getTimezones();
        return timezones.stream().map(timezoneMapper::toResponseDTO).toList();
    }


}