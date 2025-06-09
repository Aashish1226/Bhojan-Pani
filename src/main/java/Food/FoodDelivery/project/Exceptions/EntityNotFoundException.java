package Food.FoodDelivery.project.Exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public EntityNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.errorCode = "ENTITY_NOT_FOUND";
    }

    public EntityNotFoundException(String message, String errorCode) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.errorCode = errorCode;
    }

    public EntityNotFoundException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

}
