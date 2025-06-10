package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.FoodRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.FoodResponseDTO;
import Food.FoodDelivery.project.Enum.*;
import Food.FoodDelivery.project.service.FoodService;
import Food.FoodDelivery.project.validation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/foods")
@Validated
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodResponseDTO> addFood(
            @RequestPart("food") @Valid @NotNull FoodRequestDTO foodRequest,
            @RequestPart("image") @NotNull MultipartFile imageFile) {

        if (imageFile.isEmpty() || !FileValidation.validateFile(imageFile)) {
            return ResponseEntity.badRequest().build();
        }

        FoodResponseDTO response = foodService.addFood(foodRequest, imageFile);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<FoodResponseDTO>> getFoods(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String foodType,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) @Size(max = 100, message = "Search term too long") String search,
            @RequestParam(required = false) @DecimalMin(value = "0.0", message = "Min price cannot be negative") Double minPrice,
            @RequestParam(required = false) @DecimalMin(value = "0.0", message = "Max price cannot be negative") Double maxPrice,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "totalOrderCount") String sortBy,
            @RequestParam(defaultValue = "desc") @Pattern(regexp = "^(asc|desc)$", message = "Invalid sort direction , should be asc or desc") String direction
    ) {
        if (foodType != null && !FoodType.isValid(foodType)) {
            throw new IllegalArgumentException("Invalid food type: " + foodType + ". Valid types are: " + Arrays.toString(FoodType.values()));
        }

        if (!FoodSortField.isValid(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy + ". Valid fields are: " + Arrays.toString(FoodSortField.values()));
        }

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price cannot be greater than max price");
        }

        Page<FoodResponseDTO> foods = foodService.getFoods(categoryId, foodType, isAvailable, search, minPrice, maxPrice, page, size, sortBy, direction);

        return ResponseEntity.ok(foods);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodResponseDTO> updateFood(
            @PathVariable Long id,
            @RequestPart("food") @Valid FoodRequestDTO foodRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        if (imageFile != null && !imageFile.isEmpty() && !FileValidation.validateFile(imageFile)) {
            return ResponseEntity.badRequest().build();
        }

        FoodResponseDTO updatedFood = foodService.updateFood(id, foodRequest, imageFile);
        return ResponseEntity.ok(updatedFood);
    }

}
