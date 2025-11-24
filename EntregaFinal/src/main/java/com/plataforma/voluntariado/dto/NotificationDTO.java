package com.plataforma.voluntariado.dto;

import com.plataforma.voluntariado.domain.enums.NotificationChannel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record NotificationDTO(
    String notificationId,
    @NotNull NotificationChannel channel,
    @NotBlank String type,
    @NotBlank String subject,
    @NotBlank String content,
    Instant scheduledAt,
    @Min(1) @Max(5) Integer maxRetries,
    String personId,
    String activityId) {
}
