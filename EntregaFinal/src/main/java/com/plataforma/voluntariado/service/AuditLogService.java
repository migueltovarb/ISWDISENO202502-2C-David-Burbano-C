package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.AuditLog;
import com.plataforma.voluntariado.repository.AuditLogRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

  private final AuditLogRepository auditLogRepository;

  public void record(String action, String entity, UUID entityId, UUID performedBy,
      boolean success, String details, String ipAddress) {
    AuditLog log = AuditLog.builder()
        .logId(UUID.randomUUID())
        .action(action)
        .entity(entity)
        .entityId(entityId)
        .performedBy(performedBy)
        .timestamp(Instant.now())
        .ipAddress(ipAddress)
        .success(success)
        .details(details)
        .build();
    auditLogRepository.save(log);
  }
}
