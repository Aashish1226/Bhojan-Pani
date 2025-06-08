package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.PermissionRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.PermissionResponseDTO;
import Food.FoodDelivery.project.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionResponseDTO> createPermission(@RequestBody PermissionRequestDTO requestDTO) {
        return ResponseEntity.ok(permissionService.createPermission(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponseDTO>> getPermissions(
            @RequestParam(value = "isActive", required = false , defaultValue = "true") Boolean isActive) {
        List<PermissionResponseDTO> permissions = permissionService.getPermissions(isActive);
        return ResponseEntity.ok(permissions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> updatePermission(@PathVariable Long id,@RequestBody @Valid PermissionRequestDTO requestDTO) {
        PermissionResponseDTO updatedPermission = permissionService.updatePermission(id, requestDTO);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<PermissionResponseDTO> deletePermission(@PathVariable Long id) {
        PermissionResponseDTO deletedPermission = permissionService.deletePermission(id);
        return ResponseEntity.ok(deletedPermission);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<PermissionResponseDTO> restorePermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.restorePermission(id));
    }

}
