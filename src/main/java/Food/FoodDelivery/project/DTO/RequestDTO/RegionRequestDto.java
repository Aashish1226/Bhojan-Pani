package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionRequestDto {
    @NotNull(message = "Region name cannot be null")
    private String name;

    @NotNull(message = "Wiki data id cannot be null")
    private String wikiDataId;

    @NotNull(message = "SubRegion list cannot be null")
    List<SubRegionRequestDto> subRegionRequestDtoList;
}
