package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.PermissionRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.PermissionResponseDTO;
import Food.FoodDelivery.project.Entity.Permission;
import Food.FoodDelivery.project.Mapper.PermissionMapper;
import Food.FoodDelivery.project.Repository.PermissionRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Transactional
    public PermissionResponseDTO createPermission(PermissionRequestDTO requestDTO) {
        String name = requestDTO.getName().trim();
        permissionRepository.findByNameIgnoreCaseAndIsActiveTrue(name)
                .ifPresent(existing -> {
                    throw new EntityExistsException("Permission with name " + name + " already exists.");
                });
        Permission permission = permissionMapper.toEntity(requestDTO);
        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toResponseDto(saved);
    }

    public List<PermissionResponseDTO> getPermissions(Boolean isActive){
        List<Permission> permissions;
        permissions = permissionRepository.findByIsActive(isActive);

        return permissions.stream()
                .map(permissionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO requestDTO) {
        Permission permission = permissionRepository.findByIdAndIsActive(id , true)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id + " or it is not active"));

        permissionRepository.findByNameIgnoreCaseAndIsActiveTrue(requestDTO.getName().trim())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new EntityExistsException("Permission with name '" + requestDTO.getName().trim() + "' already exists.");
                });

        permissionMapper.updatePermissionFromDto(requestDTO, permission);

        Permission updated = permissionRepository.save(permission);
        return permissionMapper.toResponseDto(updated);
    }

    @Transactional
    public PermissionResponseDTO deletePermission(Long id) {
        Permission permission = permissionRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found or already inactive with id: " + id));

        permission.setIsActive(false);

        Permission updated = permissionRepository.save(permission);
        return permissionMapper.toResponseDto(updated);
    }


    @Transactional
    public PermissionResponseDTO restorePermission(Long id) {
        Permission permission = permissionRepository.findByIdAndIsActive(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found or already active with id: " + id));

        permissionRepository.findByNameIgnoreCaseAndIsActiveTrue(permission.getName().trim())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new EntityExistsException("Permission with name '" + permission.getName().trim() + "' already exists.");
                });

        permission.setIsActive(true);

        Permission updated = permissionRepository.save(permission);
        return permissionMapper.toResponseDto(updated);
    }

}
