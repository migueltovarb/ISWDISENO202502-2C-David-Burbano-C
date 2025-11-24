package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ActivityDTO(
    String activityId,
    @NotBlank @Size(min = 3, max = 80) String title,
    @NotBlank @Size(min = 10, max = 500) String description,
    @NotBlank String type,
    @NotBlank String location,
    @NotNull @Future LocalDateTime startDate,
    @NotNull @Future LocalDateTime endDate,
    @NotBlank String status,
    @Positive int requiredVolunteers,
    String coordinatorId,
    boolean active) {
}
