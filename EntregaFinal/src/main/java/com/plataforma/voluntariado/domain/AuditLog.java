package com.plataforma.voluntariado.domain;

import java.time.Instant;
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
@Document(collection = "audit_logs")
public class AuditLog {

  @Id
  private UUID logId;
  private String action;
  private String entity;
  private UUID entityId;
  @CreatedDate
  private Instant timestamp;
  private UUID performedBy;
  private String ipAddress;
  private boolean success;
  private String details;
}
