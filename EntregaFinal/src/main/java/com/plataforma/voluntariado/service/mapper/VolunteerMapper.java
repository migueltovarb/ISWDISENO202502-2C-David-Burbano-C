package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Person;
import com.plataforma.voluntariado.dto.VolunteerDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VolunteerMapper {

  @Mapping(target = "personId", source = "personId", qualifiedByName = "uuidToString")
  VolunteerDTO toDto(Person entity);

  @Mapping(target = "personId", source = "personId", qualifiedByName = "stringToUuid")
  Person toEntity(VolunteerDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
