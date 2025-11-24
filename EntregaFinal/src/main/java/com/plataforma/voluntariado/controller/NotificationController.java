package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.NotificationDTO;
import com.plataforma.voluntariado.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @PostMapping
  public NotificationDTO send(@Valid @RequestBody NotificationDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return notificationService.send(dto, requesterId);
  }

  @GetMapping("/volunteers/{id}")
  public List<NotificationDTO> listByVolunteer(@PathVariable UUID id) {
    return notificationService.listByVolunteer(id);
  }
}
