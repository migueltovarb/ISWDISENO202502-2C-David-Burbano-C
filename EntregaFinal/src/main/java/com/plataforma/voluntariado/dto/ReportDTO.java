package com.plataforma.voluntariado.dto;

import com.plataforma.voluntariado.domain.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

public record ReportDTO(
    String reportId,
    @NotNull ReportType reportType,
    @NotBlank String format,
    @NotNull LocalDate periodStart,
    @NotNull LocalDate periodEnd,
    Map<String, Object> filters) {
}
