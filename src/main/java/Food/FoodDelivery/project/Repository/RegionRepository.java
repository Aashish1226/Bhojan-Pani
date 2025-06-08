package Food.FoodDelivery.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Food.FoodDelivery.project.Entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
