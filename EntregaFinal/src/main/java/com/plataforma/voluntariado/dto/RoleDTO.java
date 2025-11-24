package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RoleDTO(
    String id,
    @NotBlank @Size(min = 3, max = 40) String name,
    @NotBlank @Size(min = 5, max = 120) String description,
    @NotEmpty List<@NotBlank String> permissions,
    boolean isDefault) {
}
