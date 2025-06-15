package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailAndIsActiveTrue(String email);
    Optional<Users> findByUserIdAndIsActiveTrue(String userId);
    boolean existsByEmailAndIsActiveTrue(String email);
    boolean existsByPhoneNumberAndIsActiveTrue(String phoneNumber);
    Optional<Users> findByIdAndIsActive(Long userId , Boolean isActive);
    boolean existsByRoleId(@NotNull(message = "Role id cannot be null") Long roleId);

    boolean existsByUserIdAndIsActiveTrue(String userUUID);
}
