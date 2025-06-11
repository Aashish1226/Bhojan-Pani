package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.*;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.*;
import Food.FoodDelivery.project.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO requestDTO) {
        roleRepository.findByNameAndIsActive(requestDTO.getName().trim(), true)
                .ifPresent(r -> {
                    throw new IllegalArgumentException("Role with name " + requestDTO.getName().trim() + "  already exists and is active.");
                });

        Role role = roleMapper.toEntity(requestDTO);
        Role saved = roleRepository.save(role);
        return roleMapper.toResponseDto(saved);
    }

    public List<RoleResponseDTO> getRoles(Boolean isActive) {
        List<Role> roles = roleRepository.findByIsActive(isActive);
        return roles.stream()
                .map(roleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<PermissionResponseDTO> getPermissionsByRoleId(Long roleId) {
        Role role = roleRepository.findByIdAndIsActive(roleId, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active role not found with id: " + roleId));

        List<Permission> permissionslist = new ArrayList<>(role.getPermissions());
        return permissionMapper.toResponseList(permissionslist);
    }

    public RoleResponseDTO updateRole(Long id, RoleRequestDTO requestDTO) {
        Role role = roleRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Role not found with id: " + id + " or role is not active"));
        String trimmedName = requestDTO.getName().trim();
        roleRepository.findByNameAndIsActive(trimmedName, true)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Another active role with name " + requestDTO.getName() + " already exists.");
                });

        role.setName(trimmedName);
        role.setDescription(requestDTO.getDescription());
        Role updated = roleRepository.save(role);
        return roleMapper.toResponseDto(updated);
    }

    public RoleResponseDTO deleteRole(Long id) {
        Role role = roleRepository.findByIdAndIsActive(id, true).orElseThrow(() -> new CustomEntityNotFoundException("Role not found with id: " + id));
        role.setIsActive(false);

        // Remove all permission associations from this role (disconnect join table entries)
//        role.getPermissions().clear();

        Role deletedRole = roleRepository.save(role);
        return roleMapper.toResponseDto(deletedRole);
    }

    @Transactional
    public RoleResponseDTO restoreRole(Long id) {
        Role role = roleRepository.findByIdAndIsActive(id, false)
                .orElseThrow(() -> new CustomEntityNotFoundException("Role not found with id: " + id + " or role is already active"));

        String trimmedName = role.getName().trim();

        roleRepository.findByNameAndIsActive(trimmedName, true)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Another active role with name " + role.getName() + " already exists.");
                });

        role.setIsActive(true);

        Role updated = roleRepository.save(role);
        return roleMapper.toResponseDto(updated);
    }

    @Transactional
    public RoleResponseDTO assignPermissionsToRole(Long roleId, AssignPermissionsToRoleRequestDTO requestDTO) {
        Role role = roleRepository.findByIdAndIsActive(roleId, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active role not found with id: " + roleId));

        List<Long> requestedIds = requestDTO.getPermissionIds();

        List<Permission> permissions = permissionRepository.findAllByIdInAndIsActiveTrue(requestedIds);

        Set<Long> foundIds = permissions.stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());

        List<Long> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("The following permission IDs are invalid or inactive: " + missingIds);
        }

        role.setPermissions(new HashSet<>(permissions));

        Role updated = roleRepository.save(role);
        return roleMapper.toResponseDto(updated);
    }

}
