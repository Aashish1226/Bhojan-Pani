package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.FoodVariantRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.FoodVariantResponseDTO;
import Food.FoodDelivery.project.service.FoodVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food-variant")
@RequiredArgsConstructor
public class FoodVariantController {

    private final FoodVariantService foodVariantService;

    @PostMapping("/food/{foodId}")
    public ResponseEntity<FoodVariantResponseDTO> addVariant(@PathVariable Long foodId, @Valid @RequestBody FoodVariantRequestDTO variantRequest) {

        FoodVariantResponseDTO response = foodVariantService.addVariant(foodId, variantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<List<FoodVariantResponseDTO>> getActiveVariantsByFood(@PathVariable Long foodId) {
        List<FoodVariantResponseDTO> variants = foodVariantService.getActiveVariantsByFood(foodId);
        return ResponseEntity.ok(variants);
    }

    @PutMapping("/food/{foodId}/variants/{variantId}")
    public ResponseEntity<FoodVariantResponseDTO> updateFoodVariant(@PathVariable Long foodId, @PathVariable Long variantId, @Valid @RequestBody FoodVariantRequestDTO variantRequest) {
        FoodVariantResponseDTO updatedVariant = foodVariantService.updateVariant(foodId, variantId, variantRequest);
        return ResponseEntity.ok(updatedVariant);
    }

    @DeleteMapping("/foods/{foodId}/variants/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long foodId, @PathVariable Long variantId) {
        foodVariantService.deleteVariant(foodId, variantId);
        return ResponseEntity.noContent().build();
    }

}
