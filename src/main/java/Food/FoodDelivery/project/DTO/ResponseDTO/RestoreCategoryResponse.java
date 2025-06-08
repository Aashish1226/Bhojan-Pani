package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestoreCategoryResponse {
    private Long categoryId;
    private String categoryName;
    private LocalDateTime restoredAt;
    private List<String> restoredFoodItems;
}
