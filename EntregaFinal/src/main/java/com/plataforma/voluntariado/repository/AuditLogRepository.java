package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.AuditLog;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLog, UUID> {
}
