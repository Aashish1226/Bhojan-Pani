package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByIsActive(Boolean isActive);
    Optional<Permission> findByNameIgnoreCaseAndIsActiveTrue(String name);

    Optional<Permission> findByIdAndIsActive(Long id, boolean b);

    List<Permission> findAllByIdInAndIsActiveTrue(List<Long> requestedIds);
}