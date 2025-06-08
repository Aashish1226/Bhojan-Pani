package Food.FoodDelivery.project.Controller;
import Food.FoodDelivery.project.DTO.RequestDTO.CartItemRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.CartResponseDTO;
import Food.FoodDelivery.project.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/increment")
    public ResponseEntity<CartResponseDTO> incrementItem(@RequestBody @Valid CartItemRequestDTO requestDTO,@RequestAttribute("userUUID") String userUUID) {
        return ResponseEntity.ok(cartService.incrementItem(requestDTO, userUUID));
    }

    @PostMapping("/decrement")
    public ResponseEntity<CartResponseDTO> decrementItem(@RequestBody CartItemRequestDTO requestDTO,@RequestAttribute("userUUID") String userUUID) {
        return ResponseEntity.ok(cartService.decrementItem(requestDTO, userUUID));
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(@RequestAttribute("userUUID") String userUUID) {
        CartResponseDTO response = cartService.getCart(userUUID);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestAttribute("userUUID") String userUUID) {
        cartService.deleteCart(userUUID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId,@RequestAttribute("userUUID") String userUUID) {
        cartService.deleteCartItem(cartItemId, userUUID);
        return ResponseEntity.noContent().build();
    }
}