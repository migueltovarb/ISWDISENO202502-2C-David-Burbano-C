package com.plataforma.voluntariado.dto;

import com.plataforma.voluntariado.domain.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageDTO(
    String messageId,
    @NotBlank String activityId,
    @NotBlank String senderId,
    String receiverId,
    @NotNull MessageType messageType,
    @NotBlank String content) {
}
