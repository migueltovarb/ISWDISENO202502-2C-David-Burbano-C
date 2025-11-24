package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Report;
import com.plataforma.voluntariado.dto.ReportDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReportMapper {

  @Mapping(target = "reportId", source = "reportId", qualifiedByName = "uuidToString")
  ReportDTO toDto(Report entity);

  @Mapping(target = "reportId", source = "reportId", qualifiedByName = "stringToUuid")
  Report toEntity(ReportDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
