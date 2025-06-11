package Food.FoodDelivery.project.Exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public class CustomEntityNotFoundException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public CustomEntityNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.errorCode = "ENTITY_NOT_FOUND";
    }

    public CustomEntityNotFoundException(String message, String errorCode) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.errorCode = errorCode;
    }

    public CustomEntityNotFoundException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

}
