package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Backup;
import com.plataforma.voluntariado.dto.BackupDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BackupMapper {

  @Mapping(target = "backupId", source = "backupId", qualifiedByName = "uuidToString")
  BackupDTO toDto(Backup entity);

  @Mapping(target = "backupId", source = "backupId", qualifiedByName = "stringToUuid")
  Backup toEntity(BackupDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
