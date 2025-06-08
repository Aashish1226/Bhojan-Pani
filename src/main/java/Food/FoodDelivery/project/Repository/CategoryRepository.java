package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Category;
import jakarta.validation.constraints.NotNull;
import org.flywaydb.core.api.output.InfoOutput;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);

    List<Category> findAllByIsActiveTrue();

    @Modifying
    @Query("""
    UPDATE Category c 
    SET c.isActive = true, c.restoredDate = :restoredAt, c.updateDate = :restoredAt
    WHERE c.id = :categoryId AND c.isActive = false
""")
    int restoreCategory(@Param("categoryId") Long categoryId, @Param("restoredAt") LocalDateTime restoredAt);


    @Modifying
    @Query("""
    UPDATE Category c 
    SET c.isActive = false, c.deletedDate = :deletedAt, c.updateDate = :deletedAt
    WHERE c.id = :categoryId AND c.isActive = true
""")
    int deactivateCategory(@Param("categoryId") Long categoryId, @Param("deletedAt") LocalDateTime deletedAt);

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findNameById(@Param("id") Long id);

    Optional<Category> findByIdAndIsActive(Long id, Boolean isActive);

    @Query("SELECT c FROM Category c WHERE c.id = :categoryId And c.isActive = true ")
    Category findByIdAndIsa(@Param("categoryId") Long categoryId);


    Optional<Category> findByNameAndIsActiveTrue(@NotNull(message = "Name is required") String name);
}
