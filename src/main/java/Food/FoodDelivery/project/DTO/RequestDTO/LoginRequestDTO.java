package Food.FoodDelivery.project.DTO.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }

}
