package Food.FoodDelivery.project.Controller;
import Food.FoodDelivery.project.DTO.RequestDTO.RegionRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<RegionResponseDto> createRegion(@Valid @RequestBody RegionRequestDto dto) {
        RegionResponseDto response = regionService.createRegion(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getRegions(
            @RequestParam(value = "regionId", required = false) Long regionId,
            @RequestParam(value = "fetchSubRegions", required = false, defaultValue = "false") boolean fetchSubRegions
    ) {
        if (regionId == null) {
            List<RegionResponseDto> regions = regionService.getAllRegions();
            return ResponseEntity.ok(regions);
        }

        if (fetchSubRegions) {
            List<SubRegionResponseDto> subRegions = regionService.getSubRegionsByRegionId(regionId);
            return ResponseEntity.ok(subRegions);
        }

        RegionResponseDto region = regionService.getRegionById(regionId);
        return ResponseEntity.ok(region);
    }

}
