package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.MessageDTO;
import com.plataforma.voluntariado.service.MessagingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessagingController {

  private final MessagingService messagingService;

  @PostMapping
  public MessageDTO postMessage(@Valid @RequestBody MessageDTO dto) {
    return messagingService.postMessage(dto);
  }

  @GetMapping("/activity/{activityId}")
  public List<MessageDTO> getThread(@PathVariable UUID activityId) {
    return messagingService.fetchThread(activityId);
  }
}
