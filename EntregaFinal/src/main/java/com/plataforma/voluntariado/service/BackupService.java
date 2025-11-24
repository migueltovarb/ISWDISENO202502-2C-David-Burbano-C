package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Backup;
import com.plataforma.voluntariado.dto.BackupDTO;
import com.plataforma.voluntariado.repository.BackupRepository;
import com.plataforma.voluntariado.service.mapper.BackupMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackupService {

  private final BackupRepository backupRepository;
  private final BackupMapper backupMapper;
  private final AuditLogService auditLogService;

  @Transactional
  public BackupDTO runManual(BackupDTO dto, UUID requesterId) {
    Backup backup = backupMapper.toEntity(dto);
    backup.setBackupId(UUID.randomUUID());
    backup.setExecutedAt(Instant.now());
    backup.setStatus("COMPLETED");
    backup.setSizeMB(10.5);
    Backup saved = backupRepository.save(backup);
    auditLogService.record("BACKUP_RUN", "Backup", saved.getBackupId(), requesterId, true,
        "Respaldo manual", null);
    return backupMapper.toDto(saved);
  }

  public List<BackupDTO> history() {
    return backupRepository.findAll().stream().map(backupMapper::toDto).toList();
  }

  @Transactional
  public BackupDTO restore(UUID backupId, UUID requesterId) {
    Backup backup = backupRepository.findById(backupId).orElseThrow();
    backup.setStatus("RESTORED");
    Backup saved = backupRepository.save(backup);
    auditLogService.record("BACKUP_RESTORE", "Backup", backupId, requesterId, true,
        "Restauración", null);
    return backupMapper.toDto(saved);
  }

  public boolean verifyIntegrity(UUID backupId) {
    return backupRepository.findById(backupId)
        .map(b -> b.isEncrypted() && b.getSizeMB() > 0)
        .orElse(false);
  }
}
