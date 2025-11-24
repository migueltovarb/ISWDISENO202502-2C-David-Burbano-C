package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Activity;
import com.plataforma.voluntariado.dto.ActivityDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "uuidToString")
  ActivityDTO toDto(Activity entity);

  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "stringToUuid")
  Activity toEntity(ActivityDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
