package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.FoodRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.FoodResponseDTO;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Enum.FoodType;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.*;
import Food.FoodDelivery.project.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final FoodMapper foodMapper;
    private final S3Service awsS3Service;
    private final FoodVariantRepository foodVariantRepository;
    private final FoodVariantMapper foodVariantMapper;
    private final S3Service s3Service;

    @Transactional
    public FoodResponseDTO addFood(FoodRequestDTO dto, MultipartFile imageFile) {
        String imageUrl = awsS3Service.uploadFileToFolder(imageFile, "food");

        Category category = categoryRepository.findByIdAndIsActive(dto.getCategoryId(), true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Category not found with ID: " + dto.getCategoryId()));

        if (foodRepository.existsByNameAndCategoryIdAndIsActiveTrue(dto.getName(), dto.getCategoryId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Food with name '" + dto.getName() + "' already exists in category '" + category.getName() + "'.");
        }

        Food food = foodMapper.toEntity(dto);
        food.setImageUrl(imageUrl);
        food.setCategory(category);

        if (Boolean.TRUE.equals(dto.getHasVariants()) && dto.getVariants() != null && !dto.getVariants().isEmpty()) {
            List<FoodVariant> variants = buildVariants(dto, food);
            food.setVariants(variants);
            food.setPrice(variants.getFirst().getPrice());
        }

        Food savedFood = foodRepository.save(food);
        return foodMapper.toDto(savedFood);
    }

    private List<FoodVariant> buildVariants(FoodRequestDTO dto, Food food) {
        return dto.getVariants().stream()
                .map(variantDto -> {
                    FoodVariant variant = foodVariantMapper.toEntity(variantDto);
                    variant.setFood(food);
                    return variant;
                }).collect(Collectors.toList());
    }

    public Page<FoodResponseDTO> getFoods(Long categoryId, String foodType, Boolean isAvailable, String search, Double minPrice, Double maxPrice, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        FoodType type = parseFoodType(foodType);
        StopWatch watch = new StopWatch();
        watch.start();
        Page<Food> pageResult = foodRepository.findByFilters(categoryId, type, isAvailable, search, minPrice, maxPrice, pageable);
        watch.stop();
        System.out.println("Query took: " + watch.getTotalTimeMillis() + " ms");
        return pageResult.map(foodMapper::toDto);
    }

    private FoodType parseFoodType(String foodType) {
        if (foodType == null || foodType.isBlank()) {
            return null;
        }
        try {
            return FoodType.valueOf(foodType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid food type: " + foodType);
        }
    }

    @Transactional
    public FoodResponseDTO updateFood(Long id, FoodRequestDTO foodRequest, MultipartFile imageFile) {
        Food food = foodRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active food not found with id: " + id));

        Category category = categoryRepository.findByIdAndIsActive(foodRequest.getCategoryId(), true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active category not found with id: " + foodRequest.getCategoryId()));

        foodMapper.updateEntityFromDto(foodRequest, food);
        food.setCategory(category);

        boolean hasVariants = foodVariantRepository.existsByFoodId(food.getId());
        if (!hasVariants) {
            food.setPrice(foodRequest.getPrice());
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3Service.uploadFileToFolder(imageFile, "food");
            food.setImageUrl(imageUrl);
        }

        Food savedFood = foodRepository.save(food);
        return foodMapper.toDto(savedFood);
    }

}
