package Food.FoodDelivery.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ServingFieldsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidServingFields {
    String message() default "If one of servingSize, servingUnit, servingQuantity is present, all must be present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
