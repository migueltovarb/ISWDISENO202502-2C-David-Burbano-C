package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record UserDTO(
    @NotBlank @Size(min = 3, max = 50) String username,
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-]).{8,}$",
        message = "Debe tener 8+ caracteres, mayúscula, número y caracter especial")
    String password,
    Set<String> roles,
    String personId) {
}
