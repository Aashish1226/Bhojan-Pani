package Food.FoodDelivery.project.Repository;
import Food.FoodDelivery.project.Entity.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameAndIsActive(String name , Boolean isActive);

    @EntityGraph(attributePaths = "permissions")
    List<Role> findByIsActive(Boolean isActive);


    Optional<Role> findByIdAndIsActive(Long id, boolean b);
}