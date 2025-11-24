package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Message;
import com.plataforma.voluntariado.dto.MessageDTO;
import com.plataforma.voluntariado.repository.MessageRepository;
import com.plataforma.voluntariado.service.mapper.MessageMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingService {

  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;

  public MessageDTO postMessage(MessageDTO dto) {
    Message message = messageMapper.toEntity(dto);
    if (message.getMessageId() == null) {
      message.setMessageId(UUID.randomUUID());
    }
    message.setCreatedAt(Instant.now());
    Message saved = messageRepository.save(message);
    return messageMapper.toDto(saved);
  }

  public List<MessageDTO> fetchThread(UUID activityId) {
    return messageRepository.findByActivityId(activityId).stream()
        .map(messageMapper::toDto)
        .toList();
  }
}
