package Food.FoodDelivery.project.Entity;

import Food.FoodDelivery.project.Enum.*;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_status", columnList = "status"),
        @Index(name = "idx_payment_method", columnList = "method"),
        @Index(name = "idx_user_uuid", columnList = "user_uuid"),
        @Index(name = "idx_razorpay_order_id", columnList = "razorpay_order_id"),
        @Index(name = "idx_failure_reason", columnList = "failure_reason")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private String userUUID;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "razorpay_status")
    private String razorpayStatus;

    @Column(name = "razorpay_method")
    private String razorpayMethod;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "vpa")
    private String vpa;

    @Column(name = "wallet_name")
    private String walletName;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_description", length = 500)
    private String errorDescription;

    @Column(name = "error_source")
    private String errorSource;

    @Column(name = "error_step")
    private String errorStep;

    @Column(name = "error_reason")
    private String errorReason;

    @Column(name = "razorpay_fee")
    private Integer razorpayFee;

    @Column(name = "razorpay_tax")
    private Integer razorpayTax;

    @Column(name = "amount_refunded")
    private Integer amountRefunded;

    @Column(name = "refund_status")
    private String refundStatus;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_contact")
    private String customerContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethod method;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "razorpay_order_id", unique = true)
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name = "razorpay_signature")
    private String razorpaySignature;

    @Column(name = "provider_response", columnDefinition = "TEXT")
    private String providerResponse;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

}
