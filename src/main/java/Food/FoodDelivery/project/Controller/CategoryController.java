package Food.FoodDelivery.project.Controller;
import Food.FoodDelivery.project.DTO.RequestDTO.CategoryRequest;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponse> addCategory(@RequestPart("category") @Valid CategoryRequest categoryRequest, @RequestPart(value = "image") MultipartFile imageFile) {
        CategoryResponse response = categoryService.addCategory(categoryRequest, imageFile);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories(); // isActive = true
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("hasAnyAuthority()")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,@RequestPart("category") @Valid CategoryRequest categoryRequest,@RequestPart(value = "image", required = false) MultipartFile imageFile) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest, imageFile);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/restore")
    public ResponseEntity<RestoreCategoryResponse> restoreCategory(@PathVariable Long id) {
        RestoreCategoryResponse response = categoryService.restoreCategory(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id,@RequestParam(name = "withFoods", defaultValue = "false") boolean withFoods) {
        CategoryResponse response = withFoods ? categoryService.deleteCategoryAndFoods(id): categoryService.deleteOnlyCategory(id);
        return ResponseEntity.ok(response);
    }

}
