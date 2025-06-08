package Food.FoodDelivery.project.validation;

import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.*;
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFile.Validator.class)
public @interface ValidFile {
    String message() default "Invalid file. File size should be less than 5MB and of type PDF, JPG, PNG, or SVG.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    class Validator implements ConstraintValidator<ValidFile, MultipartFile> {
        @Override
        public void initialize(ValidFile constraintAnnotation) {
        }
        @Override
        public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
            if (file == null || file.isEmpty()) {
                return false;
            }
            if (file.getSize() > 5 * 1024 * 1024) {
                return false;
            }
            String fileExtension = getFileExtension(file.getOriginalFilename());
            return isValidFileType(fileExtension);
             }
        private String getFileExtension(String fileName) {
            if (fileName != null && fileName.contains(".")) {
                return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            }
            return "";
        }
        private boolean isValidFileType(String fileExtension) {
            return fileExtension.equals("pdf") || fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("svg");
        }
    }
}