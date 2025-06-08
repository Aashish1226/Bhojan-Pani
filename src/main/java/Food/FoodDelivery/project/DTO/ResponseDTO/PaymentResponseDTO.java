package Food.FoodDelivery.project.DTO.ResponseDTO;

import Food.FoodDelivery.project.Enum.PaymentMethod;
import Food.FoodDelivery.project.Enum.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long id;
    private String userUUID;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String razorpayStatus;
    private String razorpayMethod;
    private String bankCode;
    private String vpa;
    private String walletName;
    private String errorCode;
    private String errorDescription;
    private String errorSource;
    private String errorStep;
    private String errorReason;
    private Integer razorpayFee;
    private Integer razorpayTax;
    private Integer amountRefunded;
    private String refundStatus;
    private String customerEmail;
    private String customerContact;
    private String method;
    private String failureReason;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String providerResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long orderId;
}
