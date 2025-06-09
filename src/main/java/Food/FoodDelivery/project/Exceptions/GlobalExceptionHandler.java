package Food.FoodDelivery.project.Exceptions;

import Food.FoodDelivery.project.DTO.ResponseDTO.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse> handleResponseStatusException(ResponseStatusException ex) {
        String message = ex.getReason();
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String errorCode = "RESPONSE_STATUS_EXCEPTION";
        return new ResponseEntity<>(CommonResponse.error(message , errorCode), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGeneralException(Exception ex) {
        String errorCode = "INTERNAL_SERVER_ERROR";
        CommonResponse errorResponse = CommonResponse.error(ex.getMessage() , errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorCode = "INVALID_ARGUMENT";
        return ResponseEntity.badRequest().body(CommonResponse.error(ex.getMessage(),errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                errors.put("validation", error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // for @RequestParam or query parameters.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String field = cv.getPropertyPath().toString();
            field = field.contains(".") ? field.substring(field.lastIndexOf('.') + 1) : field;
            errors.put(field, cv.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<CommonResponse> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        String errorCode = "AUTHORIZATION_DENIED";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResponse.error("You are not authorized for this request", errorCode));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String errorCode = "ACCESS_DENIED";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResponse.error("Access denied: You do not have permission to access this resource" , errorCode));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<CommonResponse> handlePaymentException(PaymentException ex) {
        String errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : "PAYMENT_EXCEPTION";
        String combinedMessage = ex.getErrorCode() + " - " + ex.getMessage();
        return ResponseEntity.status(ex.getStatus()).body(CommonResponse.error(combinedMessage , errorCode));
    }

    @ExceptionHandler(RazorPayCustomException.class)
    public ResponseEntity<CommonResponse> handleRazorpayException(RazorPayCustomException ex) {
        String errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : "RAZORPAY_ERROR";
        String combinedMessage = ex.getErrorCode() + " - " + ex.getMessage();
        return ResponseEntity.status(ex.getStatus()).body(CommonResponse.error(combinedMessage, errorCode));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = ex.getMessage();
        String errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : "ENTITY_NOT_FOUND";
        return ResponseEntity.status(ex.getStatus() != null ? ex.getStatus() : HttpStatus.NOT_FOUND).body(CommonResponse.error(message, errorCode));
    }

    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<CommonResponse> handleSecurityException(CustomSecurityException ex) {
        String errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : "SECURITY_VIOLATION";
        String message = ex.getMessage() != null ? ex.getMessage() : "Security violation occurred";
        return ResponseEntity.status(ex.getStatus() != null ? ex.getStatus() : HttpStatus.FORBIDDEN).body(CommonResponse.error(message, errorCode));
    }

}
