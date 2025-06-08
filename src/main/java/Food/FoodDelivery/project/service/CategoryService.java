package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.CategoryRequest;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Entity.Category;
import Food.FoodDelivery.project.Mapper.CategoryMapper;
import Food.FoodDelivery.project.Repository.*;
import Food.FoodDelivery.project.validation.FileValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final S3Service s3Service;
    private final FoodRepository foodRepository;

    @Transactional
    public CategoryResponse addCategory(CategoryRequest request, MultipartFile file) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Category with the name '" + request.getName() + "' already exists.");
        }

        if (file != null && !file.isEmpty()) {
            FileValidation.validateFile(file);
        }

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = s3Service.uploadFileToFolder(file, "categories");
        }

        Category category = categoryMapper.toEntity(request);
        category.setImageUrl(imageUrl);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByIsActiveTrue();
        return categoryMapper.toResponseList(categories);
    }

    public CategoryResponse deleteOnlyCategory(Long id) {
        Category category = categoryRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Active category not found with id: " + id));

        boolean hasFoodItems = foodRepository.existsByCategoryId(id);
        if (hasFoodItems) {
            throw new IllegalStateException("Cannot delete category because it has associated food items.");
        }

        category.setIsActive(false);
        category.setDeletedDate(LocalDateTime.now());
        Category deletedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(deletedCategory);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile imageFile) {
        Category category = categoryRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Active category not found with id: " + id));

        if (imageFile != null && !imageFile.isEmpty()) {
            FileValidation.validateFile(imageFile);
        }

        Optional<Category> existingCategory = categoryRepository.findByNameAndIsActiveTrue(request.getName())
                .stream()
                .filter(cat -> !cat.getId().equals(id))
                .findFirst();

        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("A category with name " + request.getName() + " already exists.");
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3Service.uploadFileToFolder(imageFile, "categories");
            category.setImageUrl(imageUrl);
        }

        Category updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    @Transactional
    public RestoreCategoryResponse restoreCategory(Long id) {
        LocalDateTime now = LocalDateTime.now();

        String categoryNameToRestore = categoryRepository.findNameById(id);
        if (categoryNameToRestore == null) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }

        Optional<Category> duplicateCategory = categoryRepository.findByNameAndIsActiveTrue(categoryNameToRestore)
                .stream()
                .filter(cat -> !cat.getId().equals(id))
                .findFirst();

        if (duplicateCategory.isPresent()) {
            throw new IllegalArgumentException("A category with name '" + categoryNameToRestore + "' already exists and is active. Cannot restore.");
        }

        int updatedRows = categoryRepository.restoreCategory(id, now);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("Deleted category not found with id: " + id);
        }

        foodRepository.restoreFoodsByCategory(id, now);

        List<String> restoredFoodNames = foodRepository.findFoodNamesByCategoryIdAndIsActive(id, true);

        return RestoreCategoryResponse.builder()
                .categoryId(id)
                .categoryName(categoryNameToRestore)
                .restoredAt(now)
                .restoredFoodItems(restoredFoodNames)
                .build();
    }

    @Transactional
    public CategoryResponse deleteCategoryAndFoods(Long id) {
        LocalDateTime now = LocalDateTime.now();

        int updatedCategoryRows = categoryRepository.deactivateCategory(id, now);
        if (updatedCategoryRows == 0) {
            throw new EntityNotFoundException("Active category not found with id: " + id);
        }

        foodRepository.markFoodsUnavailableByCategory(id, now);
        Optional<Category> category = categoryRepository.findByIdAndIsActive(id, true);

        return categoryMapper.toResponse(category.get());
    }


}
