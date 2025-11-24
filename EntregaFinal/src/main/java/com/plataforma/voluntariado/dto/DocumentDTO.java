package com.plataforma.voluntariado.dto;

import com.plataforma.voluntariado.domain.enums.DocumentType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DocumentDTO(
    String documentId,
    @NotNull DocumentType documentType,
    String activityId,
    @NotBlank String personId,
    @NotBlank String title,
    @NotBlank String storagePath,
    @Positive @Max(value = 5242880, message = "Máximo 5MB") long sizeBytes,
    boolean verified) {
}
