package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.ActivityDTO;
import com.plataforma.voluntariado.service.ActivityService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

  private final ActivityService activityService;

  @PostMapping
  public ActivityDTO create(@Valid @RequestBody ActivityDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return activityService.create(dto, requesterId);
  }

  @PutMapping("/{id}")
  public ActivityDTO update(@PathVariable UUID id, @Valid @RequestBody ActivityDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return activityService.update(id, dto, requesterId);
  }

  @GetMapping("/upcoming")
  public List<ActivityDTO> upcoming(@RequestParam LocalDateTime from,
      @RequestParam LocalDateTime to) {
    return activityService.upcoming(from, to);
  }
}
