package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.RoleDTO;
import com.plataforma.voluntariado.service.RoleService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

  private final RoleService roleService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RoleDTO create(@Valid @RequestBody RoleDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return roleService.create(dto, requesterId);
  }

  @PutMapping("/{id}")
  public RoleDTO update(@PathVariable UUID id, @Valid @RequestBody RoleDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return roleService.update(id, dto, requesterId);
  }

  @PostMapping("/{name}/assign/{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void assign(@PathVariable String name, @PathVariable String username,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    roleService.assignRole(name, username, requesterId);
  }

  @PostMapping("/{name}/revoke/{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revoke(@PathVariable String name, @PathVariable String username,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    roleService.revokeRole(name, username, requesterId);
  }

  @GetMapping
  public List<RoleDTO> list() {
    return roleService.list();
  }
}
