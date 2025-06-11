package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.FoodVariantRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.FoodVariantResponseDTO;
import Food.FoodDelivery.project.Entity.FoodVariant;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.FoodVariantMapper;
import Food.FoodDelivery.project.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Food.FoodDelivery.project.Entity.Food;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodVariantService {

    private final FoodVariantRepository foodVariantRepository;
    private final FoodRepository foodRepository;
    private final FoodVariantMapper foodVariantMapper;

    @Transactional
    public FoodVariantResponseDTO addVariant(Long foodId, FoodVariantRequestDTO variantRequest) {
        Food food = foodRepository.findByIdAndIsActive(foodId, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active food not found with id: " + foodId));

        boolean variantExists = foodVariantRepository.existsByFoodAndLabelIgnoreCaseAndIsActiveTrue(food, variantRequest.getLabel());
        if (variantExists) {
            throw new IllegalArgumentException("Variant with label '" + variantRequest.getLabel() + "' already exists for this food.");
        }

        FoodVariant variant = foodVariantMapper.toEntity(variantRequest);
        variant.setFood(food);

        FoodVariant savedVariant = foodVariantRepository.save(variant);
        return foodVariantMapper.toDto(savedVariant);
    }

    public List<FoodVariantResponseDTO> getActiveVariantsByFood(Long foodId) {
        Food food = foodRepository.findByIdAndIsActive(foodId, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active food not found with id: " + foodId));

        List<FoodVariant> variants = foodVariantRepository.findByFoodAndIsActiveTrue(food);
        return foodVariantMapper.toDtoList(variants);
    }

    @Transactional
    public FoodVariantResponseDTO updateVariant(Long foodId, Long variantId, FoodVariantRequestDTO variantRequest) {
        FoodVariant variant = foodVariantRepository.findByIdAndFoodIdAndIsActive(variantId, foodId)
                .orElseThrow(() -> new CustomEntityNotFoundException("Variant not found for given food or is inactive"));

        foodVariantMapper.updateEntityFromDto(variantRequest, variant);
        FoodVariant saved = foodVariantRepository.save(variant);

        return foodVariantMapper.toDto(saved);
    }

    @Transactional
    public void deleteVariant(Long foodId, Long variantId) {
        FoodVariant variant = foodVariantRepository.findByIdAndFoodIdAndIsActive(variantId, foodId)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active variant not found for this food or food is inactive"));

        variant.setIsActive(false);
        foodVariantRepository.save(variant);
    }


}
