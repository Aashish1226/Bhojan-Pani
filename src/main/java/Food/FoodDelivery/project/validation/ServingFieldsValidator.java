package Food.FoodDelivery.project.validation;

import Food.FoodDelivery.project.DTO.RequestDTO.FoodVariantRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ServingFieldsValidator implements ConstraintValidator<ValidServingFields, FoodVariantRequestDTO> {

    @Override
    public boolean isValid(FoodVariantRequestDTO dto, ConstraintValidatorContext context) {
        boolean sizePresent = dto.getServingSize() != null;
        boolean unitPresent = dto.getServingUnit() != null;
        boolean quantityPresent = dto.getServingQuantity() != null;

        return (sizePresent == unitPresent) && (unitPresent == quantityPresent);
    }
}
