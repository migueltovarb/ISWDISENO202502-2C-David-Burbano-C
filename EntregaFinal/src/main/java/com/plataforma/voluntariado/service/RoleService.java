package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Role;
import com.plataforma.voluntariado.domain.User;
import com.plataforma.voluntariado.dto.RoleDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.RoleRepository;
import com.plataforma.voluntariado.repository.UserRepository;
import com.plataforma.voluntariado.service.mapper.RoleMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final RoleMapper roleMapper;
  private final AuditLogService auditLogService;

  @Transactional
  public RoleDTO create(RoleDTO dto, UUID requesterId) {
    roleRepository.findByName(dto.name()).ifPresent(r -> {
      throw new ApiException(HttpStatus.CONFLICT, "Ya existe un rol con ese nombre");
    });
    Role role = roleMapper.toEntity(dto);
    if (role.getId() == null) {
      role.setId(UUID.randomUUID());
    }
    Role saved = roleRepository.save(role);
    auditLogService.record("ROLE_CREATE", "Role", saved.getId(), requesterId, true,
        "Creación de rol " + saved.getName(), null);
    return roleMapper.toDto(saved);
  }

  @Transactional
  public RoleDTO update(UUID id, RoleDTO dto, UUID requesterId) {
    Role role = roleRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    role.setDescription(dto.description());
    role.setPermissions(dto.permissions());
    role.setDefault(dto.isDefault());
    Role saved = roleRepository.save(role);
    auditLogService.record("ROLE_UPDATE", "Role", id, requesterId, true,
        "Actualización de rol " + role.getName(), null);
    return roleMapper.toDto(saved);
  }

  @Transactional
  public void assignRole(String roleName, String username, UUID requesterId) {
    Role role = roleRepository.findByName(roleName)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    user.getRoles().add(role.getName());
    userRepository.save(user);
    auditLogService.record("ROLE_ASSIGN", "User", user.getId(), requesterId, true,
        "Asignado rol " + roleName, null);
  }

  @Transactional
  public void revokeRole(String roleName, String username, UUID requesterId) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    user.getRoles().removeIf(r -> r.equalsIgnoreCase(roleName));
    userRepository.save(user);
    auditLogService.record("ROLE_REVOKE", "User", user.getId(), requesterId, true,
        "Revocado rol " + roleName, null);
  }

  public List<RoleDTO> list() {
    return roleRepository.findAll().stream().map(roleMapper::toDto).toList();
  }
}
