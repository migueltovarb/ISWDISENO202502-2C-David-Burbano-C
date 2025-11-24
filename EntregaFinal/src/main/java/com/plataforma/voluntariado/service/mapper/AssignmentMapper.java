package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Assignment;
import com.plataforma.voluntariado.dto.AssignmentDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

  @Mapping(target = "assignmentId", source = "assignmentId", qualifiedByName = "uuidToString")
  @Mapping(target = "volunteerId", source = "volunteerId", qualifiedByName = "uuidToString")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "uuidToString")
  AssignmentDTO toDto(Assignment entity);

  @Mapping(target = "assignmentId", source = "assignmentId", qualifiedByName = "stringToUuid")
  @Mapping(target = "volunteerId", source = "volunteerId", qualifiedByName = "stringToUuid")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "stringToUuid")
  Assignment toEntity(AssignmentDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
