package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.User;
import com.plataforma.voluntariado.dto.UserDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "username", source = "username")
  @Mapping(target = "personId", source = "personId", qualifiedByName = "stringToUuid")
  @Mapping(target = "passwordHash", ignore = true)
  @Mapping(target = "roles", source = "roles")
  User toEntity(UserDTO dto);

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
