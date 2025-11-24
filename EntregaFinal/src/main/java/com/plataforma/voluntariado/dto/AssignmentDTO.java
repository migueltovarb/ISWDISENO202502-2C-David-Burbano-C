package com.plataforma.voluntariado.dto;

import com.plataforma.voluntariado.domain.enums.AssignmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AssignmentDTO(
    String assignmentId,
    @NotBlank String volunteerId,
    @NotBlank String activityId,
    @NotNull AssignmentStatus status,
    @NotNull @Future LocalDateTime startDate,
    @NotNull @Future LocalDateTime endDate,
    double hoursWorked,
    double evaluationScore,
    String cancellationReason) {
}
