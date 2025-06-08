package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findByCountryIdOrderByNameAsc(Long country_id);
    boolean existsByNameAndCountryId(String name, Long countryId);
}