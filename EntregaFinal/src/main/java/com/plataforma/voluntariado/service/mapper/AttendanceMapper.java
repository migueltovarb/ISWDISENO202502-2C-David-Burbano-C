package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Attendance;
import com.plataforma.voluntariado.dto.AttendanceDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

  @Mapping(target = "attendanceId", source = "attendanceId", qualifiedByName = "uuidToString")
  @Mapping(target = "assignmentId", source = "assignmentId", qualifiedByName = "uuidToString")
  AttendanceDTO toDto(Attendance entity);

  @Mapping(target = "attendanceId", source = "attendanceId", qualifiedByName = "stringToUuid")
  @Mapping(target = "assignmentId", source = "assignmentId", qualifiedByName = "stringToUuid")
  Attendance toEntity(AttendanceDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : UUID.randomUUID();
  }
}
