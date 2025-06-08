package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface FoodVariantRepository extends JpaRepository<FoodVariant, Long> {

    boolean existsByFoodId(Long foodId);

    boolean existsByFoodAndLabelIgnoreCaseAndIsActiveTrue(Food food, @NotBlank(message = "Label is required") String label);

    List<FoodVariant> findByFoodAndIsActiveTrue(Food food);

    @Query("SELECT v FROM FoodVariant v WHERE v.id = :variantId AND v.food.id = :foodId AND v.isActive = true AND v.food.isActive = true")
    Optional<FoodVariant> findByIdAndFoodIdAndIsActive(Long variantId, Long foodId);

   Optional<FoodVariant> findByIdAndIsActive(Long variantId, boolean b);

    Optional<FoodVariant> findByIdAndIsActiveAndIsAvailable(Long variantId, boolean b, boolean b1);
}
