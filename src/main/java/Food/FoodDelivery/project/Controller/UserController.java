package Food.FoodDelivery.project.Controller;
import Food.FoodDelivery.project.DTO.RequestDTO.*;
import Food.FoodDelivery.project.DTO.ResponseDTO.UserResponseDTO;
import Food.FoodDelivery.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        Map<String, String> errors = userService.validateUserRequest(userRequestDTO);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.login(loginRequestDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestAttribute("userUUID") String userUUID,@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO responseDTO = userService.updateUser(userUUID, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
       UserResponseDTO deleteUser = userService.softDeleteUser(id);
        return ResponseEntity.ok(deleteUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/restore/{id}")
    public ResponseEntity<UserResponseDTO> restoreUser(@PathVariable Long id) {
        UserResponseDTO restoredUser = userService.restoreUser(id);
        return ResponseEntity.ok(restoredUser);
    }

}
