package Food.FoodDelivery.project.service;
import Food.FoodDelivery.project.DTO.RequestDTO.RegionRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.*;
import Food.FoodDelivery.project.Repository.RegionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final SubRegionMapper subRegionMapper;

    public RegionService(RegionRepository regionRepository, RegionMapper regionMapper, SubRegionMapper subRegionMapper) {
        this.regionRepository = regionRepository;
        this.subRegionMapper = subRegionMapper;
        this.regionMapper = regionMapper;
    }

    public RegionResponseDto createRegion(RegionRequestDto dto) {
        Region region = regionMapper.toEntity(dto);
        List<SubRegion> subRegionList = dto.getSubRegionRequestDtoList().stream()
                .map(subRegionDto -> {
                    SubRegion subRegion = subRegionMapper.toEntity(subRegionDto);
                    subRegion.setRegion(region);
                    return subRegion;
                })
                .toList();

        region.setSubRegionList(subRegionList);
        Region savedRegion = regionRepository.save(region);
        return regionMapper.toDto(savedRegion);
    }

    public List<RegionResponseDto> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        return regions.stream()
                .map(regionMapper::toDto)
                .collect(Collectors.toList());
    }

    public RegionResponseDto getRegionById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("Region not found with id " + id));
        return regionMapper.toDto(region);
    }

    public List<SubRegionResponseDto> getSubRegionsByRegionId(Long regionId) {
        Region region = regionRepository.findById(regionId).orElseThrow(() -> new CustomEntityNotFoundException("Region is not found with id : " + regionId));
        List<SubRegion> subRegionList = region.getSubRegionList();
        return subRegionList.stream().map(subRegionMapper::toDto).toList();
    }
}
