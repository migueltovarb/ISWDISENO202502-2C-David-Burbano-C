package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.ReportDTO;
import com.plataforma.voluntariado.service.ReportService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/dashboard")
  public Map<String, Object> dashboard(@RequestParam(required = false) LocalDate from,
      @RequestParam(required = false) LocalDate to) {
    return reportService.dashboard(from, to);
  }

  @PostMapping("/export")
  public ReportDTO export(@Valid @RequestBody ReportDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return reportService.export(dto, requesterId);
  }

  @PostMapping("/export/pdf")
  public byte[] exportPdf(@Valid @RequestBody ReportDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return reportService.exportPdfBytes(dto, requesterId);
  }

  @PostMapping("/export/excel")
  public byte[] exportExcel(@Valid @RequestBody ReportDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return reportService.exportExcelBytes(dto, requesterId);
  }
}
