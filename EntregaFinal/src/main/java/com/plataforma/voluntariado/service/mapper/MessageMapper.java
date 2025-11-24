package com.plataforma.voluntariado.service.mapper;

import com.plataforma.voluntariado.domain.Message;
import com.plataforma.voluntariado.dto.MessageDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MessageMapper {

  @Mapping(target = "messageId", source = "messageId", qualifiedByName = "uuidToString")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "uuidToString")
  @Mapping(target = "senderId", source = "senderId", qualifiedByName = "uuidToString")
  @Mapping(target = "receiverId", source = "receiverId", qualifiedByName = "uuidToString")
  MessageDTO toDto(Message entity);

  @Mapping(target = "messageId", source = "messageId", qualifiedByName = "stringToUuid")
  @Mapping(target = "activityId", source = "activityId", qualifiedByName = "stringToUuid")
  @Mapping(target = "senderId", source = "senderId", qualifiedByName = "stringToUuid")
  @Mapping(target = "receiverId", source = "receiverId", qualifiedByName = "stringToUuid")
  Message toEntity(MessageDTO dto);

  @Named("uuidToString")
  default String map(UUID id) {
    return id != null ? id.toString() : null;
  }

  @Named("stringToUuid")
  default UUID map(String id) {
    return id != null && !id.isBlank() ? UUID.fromString(id) : null;
  }
}
