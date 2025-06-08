package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^\\s*([A-Za-z]+(?:\\s+[A-Za-z]+)*)\\s*$", message = "First name must contain only letters and spaces.")
    private String firstName;

    @Pattern(regexp = "^$|^(?!\\s*$)[A-Za-z]+(?:\\s+[A-Za-z]+)*$", message = "Middle name must contain only letters and spaces.")
    private String middleName;

    @Pattern(regexp = "^$|^(?!\\s*$)[A-Za-z]+(?:\\s+[A-Za-z]+)*$", message = "Last name must contain only letters and spaces.")
    private String lastName;

    @Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use YYYY-MM-DD (ISO format).")
    private String dateOfBirth;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "country code cannot be null")
    @Pattern(regexp = "^$|^\\+\\d{1,4}$", message = "Invalid country code format. Example: +1, +91, +44")
    private String countryCode;

    @NotNull(message = "Phone Number cannot be nul")
    @Pattern(regexp = "^$|^[1-9][0-9]{9}$", message = "Phone number must be a valid 10-digit number without country code.")
    private String phoneNumber;

    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "Password must be at least 8 characters long and contain one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    private String password;

    @NotNull(message = "Role id cannot be null")
    private Long roleId;

    @NotBlank(message = "Confirm password is required.")
    private String confirmPassword;

    public void setFirstName(String firstName) {this.firstName = firstName != null ? firstName.trim() : null;}
    public void setMiddleName(String middleName) {
        this.middleName = middleName != null ? middleName.trim() : null;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName != null ? lastName.trim() : null;
    }
    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;}
}
