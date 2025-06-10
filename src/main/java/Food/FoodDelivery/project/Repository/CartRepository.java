package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserAndIsActive(Users user , Boolean isActive);

    Optional<Cart> findByIdAndIsActiveTrue(Long cartId);

    Page<Cart> findByUserIdAndIsActive(Long userId, boolean isActive, Pageable pageable);
}
