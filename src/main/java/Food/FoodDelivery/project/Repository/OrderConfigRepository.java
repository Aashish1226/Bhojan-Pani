package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.OrderConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface OrderConfigRepository extends JpaRepository<OrderConfig, Long> {

    Optional<OrderConfig> findByIdAndIsActive(Long id, boolean b);

    List<OrderConfig> findByIsActive(Boolean isActive);

}
