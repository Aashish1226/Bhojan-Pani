package Food.FoodDelivery.project.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RazorpayException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public RazorpayException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
        this.errorCode = "RAZORPAY_ERROR";
    }

    public RazorpayException(String message, String errorCode) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
        this.errorCode = errorCode;
    }

    public RazorpayException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public RazorpayException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.UNAUTHORIZED;
        this.errorCode = "RAZORPAY_ERROR";
    }
}
