package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Food;
import Food.FoodDelivery.project.Enum.FoodType;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.*;


@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Modifying
    @Query("""
                UPDATE Food f 
                SET f.isActive = true, f.updateDate = :updatedAt
                WHERE f.category.id = :categoryId AND f.isActive = false
            """)
    void restoreFoodsByCategory(@Param("categoryId") Long categoryId, @Param("updatedAt") LocalDateTime updatedAt);

    @Query("""
                SELECT f.name 
                FROM Food f 
                WHERE f.category.id = :categoryId AND f.isActive = :isActive
            """)
    List<String> findFoodNamesByCategoryIdAndIsActive(@Param("categoryId") Long categoryId, @Param("isActive") Boolean isActive);

    @Query("SELECT f FROM Food f WHERE f.id = :foodId AND f.isAvailable = :available AND f.isActive = :active")
    Optional<Food> findByIdAndAvailableAndActive(@Param("foodId") Long foodId, @Param("available") Boolean available, @Param("active") Boolean active);

    Optional<Food> findByIdAndIsActive(Long id, Boolean isActive);

    @Query("""
                SELECT f FROM Food f 
                WHERE f.category.id = :categoryId
                  AND (:status IS NULL OR f.isActive = :status)
            """)
    List<Food> findByCategoryIdAndIsActive(@Param("categoryId") Long categoryId, @Param("status") Boolean isActive);

    boolean existsByCategoryId(Long id);

    @Modifying
    @Query("""
                UPDATE Food f 
                SET f.isAvailable = false, f.updateDate = :updatedAt
                WHERE f.category.id = :categoryId AND f.isAvailable = true
            """)
    void markFoodsUnavailableByCategory(@Param("categoryId") Long categoryId, @Param("updatedAt") LocalDateTime updatedAt);

    boolean existsByNameAndCategoryIdAndIsActiveTrue(String name, Long categoryId);

    @EntityGraph(attributePaths = {"variants"})
    @Query("""
                SELECT f FROM Food f
                WHERE (:categoryId IS NULL OR f.category.id = :categoryId)
                  AND (:foodType IS NULL OR f.foodType = :foodType)
                  AND (:isAvailable IS NULL OR f.isAvailable = :isAvailable)
                  AND (:search IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%')))
                  AND (:minPrice IS NULL OR f.price >= :minPrice)
                  AND (:maxPrice IS NULL OR f.price <= :maxPrice)
                  AND f.isActive = true
            """)
    Page<Food> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("foodType") FoodType foodType,
            @Param("isAvailable") Boolean isAvailable,
            @Param("search") String search,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );

    Optional<Food> findByIdAndIsAvailableAndIsActive(Long foodId, boolean b, boolean b1);

}