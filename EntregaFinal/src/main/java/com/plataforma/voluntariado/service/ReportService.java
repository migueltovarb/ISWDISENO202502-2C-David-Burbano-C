package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Report;
import com.plataforma.voluntariado.domain.enums.AssignmentStatus;
import com.plataforma.voluntariado.dto.ReportDTO;
import com.plataforma.voluntariado.repository.ActivityRepository;
import com.plataforma.voluntariado.repository.AssignmentRepository;
import com.plataforma.voluntariado.repository.PersonRepository;
import com.plataforma.voluntariado.repository.ReportRepository;
import com.plataforma.voluntariado.service.mapper.ReportMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final ReportRepository reportRepository;
  private final ReportMapper reportMapper;
  private final ActivityRepository activityRepository;
  private final PersonRepository personRepository;
  private final AssignmentRepository assignmentRepository;
  private final AuditLogService auditLogService;

  public Map<String, Object> dashboard(LocalDate from, LocalDate to) {
    LocalDateTime start = from != null ? from.atStartOfDay() : LocalDateTime.now();
    LocalDateTime end = to != null ? to.atTime(23, 59) : LocalDateTime.now().plusDays(7);
    Map<String, Object> data = new HashMap<>();
    data.put("voluntarios", personRepository.count());
    data.put("actividades", activityRepository.count());
    data.put("asignaciones", assignmentRepository.count());
    data.put("asignacionesCompletadas", assignmentRepository.countByStatus(
        AssignmentStatus.COMPLETED));
    data.put("actividadesProximas",
        activityRepository.findByStartDateBetween(start, end).size());
    data.put("topVoluntarios", topVolunteers());
    return data;
  }

  @Transactional
  public ReportDTO export(ReportDTO dto, UUID requesterId) {
    Report report = reportMapper.toEntity(dto);
    if (report.getReportId() == null) {
      report.setReportId(UUID.randomUUID());
    }
    report.setGeneratedAt(Instant.now());
    report.setCreatedBy(requesterId);
    Report saved = reportRepository.save(report);
    auditLogService.record("REPORT_EXPORT", "Report", saved.getReportId(), requesterId, true,
        "Export " + saved.getReportType(), null);
    return reportMapper.toDto(saved);
  }

  public byte[] exportPdfBytes(ReportDTO dto, UUID requesterId) {
    ReportDTO saved = export(dto, requesterId);
    // Placeholder: en producción usar Jasper/Apache POI
    String content = "Reporte " + saved.reportType() + " generado el " + saved.periodStart()
        + " - " + saved.periodEnd();
    return content.getBytes();
  }

  public byte[] exportExcelBytes(ReportDTO dto, UUID requesterId) {
    ReportDTO saved = export(dto, requesterId);
    String header = "tipo,desde,hasta\n" + saved.reportType() + "," + saved.periodStart() + ","
        + saved.periodEnd();
    return header.getBytes();
  }

  private Map<String, Long> topVolunteers() {
    return assignmentRepository.findAll().stream()
        .collect(Collectors.groupingBy(a -> a.getVolunteerId().toString(), Collectors.counting()))
        .entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(5)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
