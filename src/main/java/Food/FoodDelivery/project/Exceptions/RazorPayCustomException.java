package Food.FoodDelivery.project.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RazorPayCustomException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public RazorPayCustomException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = "RAZORPAY_ERROR";
    }

    public RazorPayCustomException(String message, String errorCode) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
        this.errorCode = errorCode;
    }

    public RazorPayCustomException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

}
