package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.OrderConfigRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.OrderConfigResponseDTO;
import Food.FoodDelivery.project.service.OrderConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order-config")
@RequiredArgsConstructor
public class OrderConfigController {

    private final OrderConfigService orderConfigService;

    @GetMapping
    public ResponseEntity<List<OrderConfigResponseDTO>> getOrderConfigs(
            @RequestParam(name = "isActive", defaultValue = "true") Boolean isActive) {
        List<OrderConfigResponseDTO> configs = orderConfigService.getOrderConfigs(isActive);
        return ResponseEntity.ok(configs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderConfigResponseDTO> updateOrderConfig(@PathVariable Long id,@RequestBody OrderConfigRequestDTO requestDTO) {
        OrderConfigResponseDTO updated = orderConfigService.updateOrderConfig(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteOrderConfig(@PathVariable Long id) {
        orderConfigService.softDeleteOrderConfig(id);
        return ResponseEntity.noContent().build();
    }

}
