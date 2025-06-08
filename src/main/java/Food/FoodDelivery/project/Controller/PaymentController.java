package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.PaymentRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.PaymentResponseDTO;
import Food.FoodDelivery.project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController{

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDTO> createPaymentOrder(@RequestBody PaymentRequestDTO requestDTO,@RequestAttribute("userUUID") String userUUID) {
        PaymentResponseDTO response = paymentService.createRazorPayOrder(requestDTO, userUUID);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<PaymentResponseDTO> verifyPayment(@RequestParam String razorpayPaymentId,@RequestParam String razorpayOrderId,@RequestParam String razorpaySignature) {
        log.info("Received request to verify Razorpay payment: orderId={}, paymentId={}", razorpayOrderId, razorpayPaymentId);
        PaymentResponseDTO response = paymentService.verifyAndSavePayment(razorpayPaymentId, razorpayOrderId, razorpaySignature);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        Optional<PaymentResponseDTO> paymentOpt = paymentService.getPaymentByOrderId(orderId);
        return paymentOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsForUser(@RequestAttribute("userUUID") String userUUID) {
        List<PaymentResponseDTO> responseList = paymentService.getUserPaymentHistory(userUUID);
        return ResponseEntity.ok(responseList);
    }

}
