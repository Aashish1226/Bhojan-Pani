package Food.FoodDelivery.project.DTO.ResponseDTO;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private LocalDateTime deletedDate;

    private LocalDateTime restoredDate;
}