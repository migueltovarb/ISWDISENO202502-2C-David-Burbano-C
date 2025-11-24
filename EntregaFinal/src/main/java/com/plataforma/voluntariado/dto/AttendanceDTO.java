package com.plataforma.voluntariado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceDTO(
    String attendanceId,
    @NotBlank String assignmentId,
    @NotNull LocalDate date,
    boolean present,
    @NotNull LocalTime arrivalTime,
    @NotNull LocalTime departureTime,
    String performanceLevel,
    String observations) {
}
