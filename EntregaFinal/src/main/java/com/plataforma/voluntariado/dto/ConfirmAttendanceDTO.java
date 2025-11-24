package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmAttendanceDTO(
    @NotBlank String assignmentId,
    @NotBlank String code) {
}
