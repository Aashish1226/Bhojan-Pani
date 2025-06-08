package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Payments;
import Food.FoodDelivery.project.Enum.PaymentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    Optional<Payments> findByOrdersId(Long orderId);

    Optional<Payments> findByRazorpayOrderId(String razorpayOrderId);

    List<Payments> findByUserUUIDOrderByCreatedAtDesc(String userUUID);

    boolean existsByOrdersIdAndStatus(Long orderId, PaymentStatus status);

    @Modifying
    @Query("UPDATE Payments p SET p.status = :status, p.failureReason = :reason, p.updatedAt = :updatedAt WHERE p.id = :paymentId")
    void updatePaymentStatus(@Param("paymentId") Long paymentId,
                             @Param("status") PaymentStatus status,
                             @Param("reason") String reason,
                             @Param("updatedAt") LocalDateTime updatedAt);


    // In PaymentRepository
    @Modifying
    @Query("UPDATE Payments p SET p.status = 'FAILED', p.updatedAt = :updatedAt, p.failureReason = 'Superseded by new payment attempt' WHERE p.orders.id = :orderId AND p.status = 'CREATED'")
    int markCreatedPaymentsAsFailed(@Param("orderId") Long orderId, @Param("updatedAt") LocalDateTime updatedAt);
}
