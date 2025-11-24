package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecoveryRequestDTO(
    @Email @NotBlank String email) {
}
