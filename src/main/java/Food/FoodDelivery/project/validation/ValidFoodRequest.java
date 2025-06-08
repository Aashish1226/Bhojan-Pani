package Food.FoodDelivery.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FoodRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFoodRequest {
    String message() default "Invalid Food request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
