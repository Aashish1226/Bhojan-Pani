package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.PaymentRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.PaymentResponseDTO;
import Food.FoodDelivery.project.Entity.Orders;
import Food.FoodDelivery.project.Entity.Payments;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Enum.*;
import Food.FoodDelivery.project.Exceptions.RazorPayCustomException;
import Food.FoodDelivery.project.Exceptions.*;
import Food.FoodDelivery.project.Mapper.PaymentMapper;
import Food.FoodDelivery.project.Repository.*;
import com.razorpay.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static Food.FoodDelivery.project.service.EmailService.buildFailureEmailBody;
import static Food.FoodDelivery.project.service.EmailService.buildSuccessEmailBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final RazorpayClient razorpayClient;
    private final CartRepository cartRepository;
    private final EmailService emailService;

    @Value("${razorpay.secret_key}")
    private String razorpayKeySecret;

    @Value("${payment.max-attempts}")
    private int maxPaymentAttempts;

    @Transactional
    public PaymentResponseDTO createRazorPayOrder(PaymentRequestDTO requestDTO, String userUUID) {
        try {
            log.info("Initiating payment for userUUID: {}", userUUID);

            Orders orders = validateUserAndGetOrder(requestDTO, userUUID);

            validatePaymentAmount(requestDTO, orders);

            boolean anySuccess = paymentRepository.existsByOrdersIdAndStatus(orders.getId(), PaymentStatus.SUCCESS);

            if (anySuccess) {
                throw new PaymentException("Payment already completed for this order");
            }

            int updatedCount = paymentRepository.markCreatedPaymentsAsFailed(orders.getId(), LocalDateTime.now());
            if (updatedCount > 0) {
                log.info("Marked {} CREATED payments as FAILED for order {}", updatedCount, orders.getId());
            }

            orders.setPaymentAttempts(orders.getPaymentAttempts() + 1);

            if (orders.getPaymentAttempts() > maxPaymentAttempts) {
                cancelOrderForMaxAttempts(orders.getId());
                return PaymentResponseDTO.builder().failureReason("Maximum payment attempts exceeded. Order cancelled.").build();
            }

            orderRepository.save(orders);
            JSONObject options = getJsonObject(requestDTO, userUUID, orders);
            Payments payments = createAndSavePayment(requestDTO, userUUID, orders, options);

            log.info("Successfully created Razorpay order for payment {}", payments.getId());
            return paymentMapper.toResponseDTO(payments);

        } catch (RazorpayException e) {
            log.error("Failed to create Razorpay order: {}", e.getMessage(), e);
            throw new RazorPayCustomException("Failed to create Razorpay order : " + e.getMessage());
        } catch (Exception e) {
            log.error("Failed to create payment: {}", e.getMessage(), e);
            throw new PaymentException("Failed to create payment : " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelOrderForMaxAttempts(Long orderId) {
        Orders orders = orderRepository.findByIdAndIsActive(orderId , true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Order not found: " + orderId));
        orders.setIsActive(false);
        orders.setOrderStatus(OrderStatus.CANCELLED);
        Orders cancelledOrder = orderRepository.save(orders);
        String html = EmailService.buildOrderFailureHtmlEmailBody(cancelledOrder, "Maximum payment attempts exceeded.");
        emailService.sendHtmlEmail(cancelledOrder.getCart().getUser().getEmail(), "❌ Order Failed - " + cancelledOrder.getOrderNumber(), html);

        log.warn("Order {} cancelled due to maximum payment attempts exceeded", orderId);
    }

    @Transactional
    public Payments createAndSavePayment(PaymentRequestDTO requestDTO, String userUUID, Orders orders, JSONObject options) throws com.razorpay.RazorpayException {
        Order razorpayOrder = razorpayClient.orders.create(options);

        Payments payments = Payments.builder()
                .userUUID(userUUID)
                .orders(orders)
                .amount(requestDTO.getAmount())
                .currency(requestDTO.getCurrency())
                .status(PaymentStatus.CREATED)
                .razorpayOrderId(razorpayOrder.get("id"))
                .createdAt(LocalDateTime.now())
                .build();
        return paymentRepository.save(payments);
    }

    private Orders validateUserAndGetOrder(PaymentRequestDTO requestDTO, String userUUID) {
        if (!userRepository.existsByUserIdAndIsActiveTrue(userUUID)) {
            throw new CustomEntityNotFoundException("User not found: " + userUUID);
        }

        Orders orders = orderRepository.findByIdAndIsActive(requestDTO.getOrderId(), true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Order not found: " + requestDTO.getOrderId()));

        if (!orders.getCart().getUser().getUserId().equals(userUUID)) {
            throw new CustomSecurityException("Order does not belong to user");
        }

        return orders;
    }

    private void validatePaymentAmount(PaymentRequestDTO requestDTO, Orders orders) {
        if (requestDTO.getAmount().compareTo(orders.getFinalAmount()) != 0) {
            throw new PaymentException("Payment amount does not match order total");
        }
    }

    private static JSONObject getJsonObject(PaymentRequestDTO requestDTO, String userUUID, Orders orders) {
        JSONObject options = new JSONObject();

        int amountInPaise = requestDTO.getAmount().multiply(new BigDecimal(100)).intValue();
        options.put("amount", amountInPaise);
        options.put("currency", requestDTO.getCurrency());
        options.put("receipt", "order_rcpt_" + orders.getOrderNumber());
        options.put("payment_capture", 1);

        JSONObject notes = new JSONObject();
        notes.put("order_id", orders.getId());
        notes.put("order_number", orders.getOrderNumber());
        notes.put("user_uuid", userUUID);
        notes.put("created_by", "FoodDeliveryApp");
        options.put("notes", notes);
        return options;
    }

    @Transactional
    public PaymentResponseDTO verifyAndSavePayment(String razorpayPaymentId, String razorpayOrderId, String razorpaySignature) {
        try {
            log.info("Verifying payment. Order ID: {}, Payment ID: {}", razorpayOrderId, razorpayPaymentId);

            Payments payments = paymentRepository.findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(() -> new CustomEntityNotFoundException("Payment not found for Razorpay Order ID: " + razorpayOrderId));

            if (payments.getStatus() != PaymentStatus.CREATED) {
                throw new PaymentException("Payment is not in a valid state for verification.");
            }

            String payload = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValidSignature = Utils.verifySignature(payload, razorpaySignature, razorpayKeySecret);

            if (!isValidSignature) {
                handleFailureEmail(payments);
                throw new RazorPayCustomException("Invalid Razorpay signature");
            }

            try {
                Payment razorpayPayment = razorpayClient.payments.fetch(razorpayPaymentId);

                extractAndSetRazorpayData(payments, razorpayPayment);
                payments.setProviderResponse(razorpayPayment.toString());

                String razorpayStatus = razorpayPayment.get("status");
                if ("captured".equals(razorpayStatus)) {
                    payments.setStatus(PaymentStatus.SUCCESS);
                } else if ("failed".equals(razorpayStatus)) {
                    handleFailureEmail(payments);
                    return paymentMapper.toResponseDTO(payments);
                } else {
                    payments.setStatus(PaymentStatus.PENDING);
                }

            } catch (RazorpayException e) {
                handleFailureEmail(payments);
                throw new RazorPayCustomException("Failed to fetch Razorpay payment: " + e.getMessage());
            }

            payments.setRazorpayPaymentId(razorpayPaymentId);
            payments.setRazorpaySignature(razorpaySignature);
            payments.setUpdatedAt(LocalDateTime.now());
            payments = paymentRepository.save(payments);

            if (payments.getStatus() == PaymentStatus.SUCCESS) {
                if (payments.getCustomerEmail() != null) {
                    emailService.sendEmail(payments.getCustomerEmail(), "Payment Successful - " + payments.getOrders().getOrderNumber(), buildSuccessEmailBody(payments));
                }
                Orders orders = payments.getOrders();
                if (orders == null) {
                    throw new IllegalArgumentException("Order not found for payment");
                }
                emailService.sendEmail(orders.getCart().getUser().getEmail(), "Payment Successful - " + orders.getOrderNumber(), buildSuccessEmailBody(payments));
                markOrderAsConfirmed(orders);
                orderRepository.save(orders);

                Cart cart = orders.getCart();
                if (cart != null) {
                    deactivateCart(cart);
                }
            }

            return paymentMapper.toResponseDTO(payments);

        } catch (RazorpayException e) {
            throw new RazorPayCustomException("Razor pay exception : " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during payment verification: {}", e.getMessage(), e);
            throw new PaymentException("Unexpected error during payment verification: " + e.getMessage());
        }
    }

    private void extractAndSetRazorpayData(Payments payments, Payment razorpayPayment) {
        try {
            JSONObject json = razorpayPayment.toJson();

            payments.setRazorpayStatus(json.optString("status", null));
            payments.setRazorpayMethod(json.optString("method", null));
            payments.setCustomerEmail(json.optString("email", null));
            payments.setCustomerContact(json.optString("contact", null));

            payments.setRazorpayFee(json.optInt("fee", 0));
            payments.setRazorpayTax(json.optInt("tax", 0));
            payments.setAmountRefunded(json.optInt("amount_refunded", 0));
            payments.setRefundStatus(json.optString("refund_status", null));

            String method = payments.getRazorpayMethod();
            if (method != null) {
                switch (method.toLowerCase()) {
                    case "upi":
                        JSONObject upi = json.optJSONObject("upi");
                        if (upi != null) {
                            payments.setVpa(upi.optString("vpa", null));
                        }
                        break;

                    case "netbanking":
                        payments.setBankCode(json.optString("bank", null));
                        break;

                    case "card":
                        if (json.has("bank")) {
                            payments.setBankCode(json.optString("bank", null));
                        } else {
                            JSONObject cardObj = json.optJSONObject("card");
                            if (cardObj != null) {
                                payments.setBankCode(cardObj.optString("issuer", null));
                            }
                        }
                        break;
                    case "wallet":
                        payments.setWalletName(json.optString("wallet", null));
                        break;
                }
            }
            setRazorPayErrorsAndProviderResponse(json , payments);
        } catch (Exception e) {
            handleFailureEmail(payments);
            log.error("Error extracting Razorpay data: {}", e.getMessage(), e);
            throw new RazorPayCustomException("Error extracting Razorpay data: " + e.getMessage());
        }
    }

    void setRazorPayErrorsAndProviderResponse(JSONObject json , Payments payments){
        if (json.has("error_code")) {
            payments.setErrorCode(json.optString("error_code", null));
            payments.setErrorDescription(json.optString("error_description", null));
            payments.setErrorSource(json.optString("error_source", null));
            payments.setErrorStep(json.optString("error_step", null));
            payments.setErrorReason(json.optString("error_reason", null));

            if (payments.getErrorDescription() != null && !payments.getErrorDescription().trim().isEmpty()) {
                payments.setFailureReason(payments.getErrorDescription());
            }
        }
        payments.setProviderResponse(json.toString());
    }

    public Optional<PaymentResponseDTO> getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrdersId(orderId)
                .map(paymentMapper::toResponseDTO);
    }

    public List<PaymentResponseDTO> getUserPaymentHistory(String userUUID) {
        List<Payments> payments = paymentRepository.findByUserUUIDOrderByCreatedAtDesc(userUUID);
        return payments.stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void handleFailureEmail(Payments payments) {
        String email = payments.getCustomerEmail(); // that razor pay sets
        if (email == null) {
            email = payments.getOrders().getCart().getUser().getEmail();
        }
        emailService.sendEmail(email, "Payment Failed - " + payments.getOrders().getOrderNumber(), buildFailureEmailBody(payments));
    }

    public void markOrderAsConfirmed(Orders orders) {
        log.info("Marking order {} as paid", orders.getId());
        orders.setOrderStatus(OrderStatus.CONFIRMED);
       Orders savedOrder =  orderRepository.save(orders);
        String html = EmailService.buildOrderSuccessHtmlEmailBody(savedOrder);
        emailService.sendHtmlEmail(savedOrder.getCart().getUser().getEmail(), "🎉 Order Confirmed - " + savedOrder.getOrderNumber(), html);
    }

    private void deactivateCart(Cart cart) {
        if (cart != null && Boolean.TRUE.equals(cart.getIsActive())) {
            cart.setIsActive(false);
            cartRepository.save(cart);
            log.info("Cart {} marked as inactive after successful payment", cart.getId());
        }
    }
}
