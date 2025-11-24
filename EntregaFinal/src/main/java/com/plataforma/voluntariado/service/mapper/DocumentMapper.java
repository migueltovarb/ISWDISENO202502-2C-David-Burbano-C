package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Document;
import com.plataforma.voluntariado.dto.DocumentDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

  @Mapping(target = "documentId", source = "documentId", qualifiedByName = "uuidToString")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "uuidToString")
  @Mapping(target = "personId", source = "personId", qualifiedByName = "uuidToString")
  DocumentDTO toDto(Document entity);

  @Mapping(target = "documentId", source = "documentId", qualifiedByName = "stringToUuid")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "stringToUuid")
  @Mapping(target = "personId", source = "personId", qualifiedByName = "stringToUuid")
  Document toEntity(DocumentDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : null;
  }
}
