package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record VolunteerDTO(
    String personId,
    @NotBlank @Size(min = 5, max = 80) String fullName,
    @Email @NotBlank String email,
    @Pattern(regexp = "^[0-9+\\-\\s]{7,20}$") String phone,
    @NotNull LocalDate birthDate,
    @NotBlank @Size(min = 5, max = 120) String address,
    @NotBlank @Size(min = 5, max = 30) String documentId,
    @NotBlank String roleType,
    boolean active,
    String notificationPreferences) {
}
