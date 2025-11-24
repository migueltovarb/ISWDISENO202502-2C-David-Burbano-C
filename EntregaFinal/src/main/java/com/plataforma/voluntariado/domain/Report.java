package com.plataforma.voluntariado.domain;

import com.plataforma.voluntariado.domain.enums.ReportType;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reports")
public class Report {

  @Id
  private UUID reportId;

  private ReportType reportType;
  private String format;
  @CreatedDate
  private Instant generatedAt;
  private LocalDate periodStart;
  private LocalDate periodEnd;
  private Map<String, Object> filters;
  private UUID createdBy;
}
