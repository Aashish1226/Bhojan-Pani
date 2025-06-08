package Food.FoodDelivery.project.Exceptions;

import lombok.Getter;

import java.util.Map;
@Getter
public class FieldValidationException extends RuntimeException{
    private final Map<String, String> errorMap;
    public FieldValidationException(Map<String, String> errorMap) {
        super("Validation failed");
        this.errorMap = errorMap;
    }
}