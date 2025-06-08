package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserAndIsActiveTrue(Users user);

    Optional<Cart> findByIdAndIsActiveTrue(Long cartId);
}
