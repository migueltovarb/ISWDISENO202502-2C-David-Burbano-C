package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.ActivityDTO;
import com.plataforma.voluntariado.dto.VolunteerDTO;
import com.plataforma.voluntariado.service.VolunteerService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerController {

  private final VolunteerService volunteerService;

  @GetMapping("/{id}")
  public VolunteerDTO getProfile(@PathVariable UUID id) {
    return volunteerService.getProfile(id);
  }

  @PutMapping("/{id}")
  public VolunteerDTO updateProfile(@PathVariable UUID id, @Valid @RequestBody VolunteerDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return volunteerService.updateProfile(id, dto, requesterId);
  }

  @GetMapping("/{id}/calendar")
  public List<ActivityDTO> getCalendar(@PathVariable UUID id) {
    return volunteerService.getCalendar(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VolunteerDTO register(@Valid @RequestBody VolunteerDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return volunteerService.registerVolunteer(dto, requesterId);
  }
}
