package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Backup;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BackupRepository extends MongoRepository<Backup, UUID> {

  List<Backup> findByEncrypted(boolean encrypted);
}
