package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubRegionRequestDto {
    @NotNull(message = "sub-region name is not null")
    private String name;
    @NotNull(message = "wiki data id name is not null")
    private String wikiDataId;
}
