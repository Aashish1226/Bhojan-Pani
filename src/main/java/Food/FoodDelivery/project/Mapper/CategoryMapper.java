package Food.FoodDelivery.project.Mapper;

import Food.FoodDelivery.project.DTO.RequestDTO.CategoryRequest;
import Food.FoodDelivery.project.DTO.ResponseDTO.CategoryResponse;
import Food.FoodDelivery.project.Entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);

    List<Category> toEntityList(List<CategoryRequest> requestList);

    List<CategoryResponse> toResponseList(List<Category> categoryList);
}
