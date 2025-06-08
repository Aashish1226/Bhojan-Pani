package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.*;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO createdRole = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getActiveRoles(@RequestParam(value = "isActive", required = false , defaultValue = "true") Boolean isActive) {
        return ResponseEntity.ok(roleService.getRoles(isActive));
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<List<PermissionResponseDTO>> getPermissionsByRole(@PathVariable Long roleId) {
        List<PermissionResponseDTO> permissions = roleService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id,@Valid @RequestBody RoleRequestDTO requestDTO){
        return ResponseEntity.ok(roleService.updateRole(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> deleteRole(@PathVariable Long id) {
       RoleResponseDTO deleteRole =  roleService.deleteRole(id);
        return ResponseEntity.ok(deleteRole);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<RoleResponseDTO> restoreRole(@PathVariable Long id) {
        RoleResponseDTO restoredRole = roleService.restoreRole(id);
        return ResponseEntity.ok(restoredRole);
    }

    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<RoleResponseDTO> assignPermissionsToRole(@PathVariable Long roleId,@RequestBody AssignPermissionsToRoleRequestDTO requestDTO){
        RoleResponseDTO updated = roleService.assignPermissionsToRole(roleId, requestDTO);
        return ResponseEntity.ok(updated);
    }

}
