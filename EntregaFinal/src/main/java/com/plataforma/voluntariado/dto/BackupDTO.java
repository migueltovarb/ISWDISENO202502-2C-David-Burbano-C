package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.NotBlank;

public record BackupDTO(
    String backupId,
    @NotBlank String frequency,
    boolean encrypted) {
}
