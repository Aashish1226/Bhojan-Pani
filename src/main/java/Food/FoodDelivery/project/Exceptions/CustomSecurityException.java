package Food.FoodDelivery.project.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomSecurityException extends RuntimeException {
    String errorCode;
    HttpStatus status;

    public CustomSecurityException(String message) {
        super(message);
        this.errorCode = "SECURITY_VIOLATION";
        this.status = HttpStatus.FORBIDDEN;
    }

    public CustomSecurityException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomSecurityException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

}
