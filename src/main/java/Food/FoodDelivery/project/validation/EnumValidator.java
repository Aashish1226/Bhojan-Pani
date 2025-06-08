package Food.FoodDelivery.project.validation;

import jakarta.validation.*;

import java.lang.annotation.*;
import java.util.stream.Stream;
@Documented
@Constraint(validatedBy = EnumValidator.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid value. Allowed values: {enumValues}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    class Validator implements ConstraintValidator<EnumValidator, String> {
        private String[] enumValues;
        @Override
        public void initialize(EnumValidator annotation) {
            enumValues = Stream.of(annotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .toArray(String[]::new);
        }
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
            boolean isValid = Stream.of(enumValues)
                    .anyMatch(validValue -> validValue.equalsIgnoreCase(value));
            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid Enum Type. Allowed values: " + String.join(", ", enumValues))
                        .addConstraintViolation();
            }
            return isValid;
        }
    }
}
