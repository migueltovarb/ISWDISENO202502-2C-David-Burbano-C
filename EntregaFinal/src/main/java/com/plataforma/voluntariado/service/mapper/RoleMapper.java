package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Role;
import com.plataforma.voluntariado.dto.RoleDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString")
  RoleDTO toDto(Role entity);

  @Mapping(target = "id", source = "id", qualifiedByName = "stringToUuid")
  Role toEntity(RoleDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
