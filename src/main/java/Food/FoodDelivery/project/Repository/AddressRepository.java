package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Addresses;
import Food.FoodDelivery.project.Enum.AddressType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Addresses, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Addresses a WHERE a.user.id = :userId AND a.addressType = :type")
    boolean existsByUserAndAddressType(@Param("userId") Long userId, @Param("type") AddressType addressType);

    List<Addresses> findByUserIdAndIsActive(Long id, boolean b);

    Addresses findByIdAndIsActive(Long addressId, boolean b);
}
