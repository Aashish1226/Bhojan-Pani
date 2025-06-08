package Food.FoodDelivery.project.DTO.RequestDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListRequest {
    private List<CategoryRequest> categories;
}