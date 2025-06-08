package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Orders;
import Food.FoodDelivery.project.Enum.OrderStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("""
    SELECT o FROM Orders o
    WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
      AND (:isActive IS NULL OR (:isActive = TRUE AND o.orderStatus <> 'DELIVERED') OR (:isActive = FALSE AND o.orderStatus = 'DELIVERED'))
      AND (:fromDate IS NULL OR o.orderPlacedAt >= :fromDate)
      AND (:toDate IS NULL OR o.orderPlacedAt <= :toDate)
      AND (:minAmount IS NULL OR o.finalAmount >= :minAmount)
      AND (:maxAmount IS NULL OR o.finalAmount <= :maxAmount)
""")
    Page<Orders> findByFilters(
            @Param("orderStatus") OrderStatus orderStatus,
            @Param("isActive") Boolean isActive,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount,
            Pageable pageable
    );

    Optional<Orders> findByIdAndIsActive(Long orderId, boolean b);

    boolean existsByCartIdAndOrderStatus(Long cartId, OrderStatus orderStatus);


}

