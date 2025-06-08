package Food.FoodDelivery.project.validation;

import Food.FoodDelivery.project.DTO.RequestDTO.FoodRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FoodRequestValidator implements ConstraintValidator<ValidFoodRequest, FoodRequestDTO> {

    @Override
    public boolean isValid(FoodRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        boolean valid = true;

        if (Boolean.TRUE.equals(dto.getHasVariants())) {
            if (dto.getVariants() == null || dto.getVariants().isEmpty()) {
                valid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Variants list cannot be empty when hasVariants is true")
                        .addPropertyNode("variants")
                        .addConstraintViolation();
            }
        } else {
            if (dto.getPrice() == null || dto.getPrice() <= 0) {
                valid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Price must be greater than zero when hasVariants is false")
                        .addPropertyNode("price")
                        .addConstraintViolation();
            }
        }

        return valid;
    }
}
