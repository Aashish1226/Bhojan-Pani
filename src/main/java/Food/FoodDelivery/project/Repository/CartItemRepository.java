package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
