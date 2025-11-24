package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Notification;
import com.plataforma.voluntariado.dto.NotificationDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

  @Mapping(target = "notificationId", source = "notificationId", qualifiedByName = "uuidToString")
  @Mapping(target = "personId", source = "personId", qualifiedByName = "uuidToString")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "uuidToString")
  NotificationDTO toDto(Notification entity);

  @Mapping(target = "notificationId", source = "notificationId", qualifiedByName = "stringToUuid")
  @Mapping(target = "personId", source = "personId", qualifiedByName = "stringToUuid")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "stringToUuid")
  Notification toEntity(NotificationDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : null;
  }
}
