package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.BackupDTO;
import com.plataforma.voluntariado.service.BackupService;
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
@RequestMapping("/api/backups")
@RequiredArgsConstructor
public class BackupController {

  private final BackupService backupService;

  @PostMapping("/run")
  public BackupDTO run(@Valid @RequestBody BackupDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return backupService.runManual(dto, requesterId);
  }

  @GetMapping
  public List<BackupDTO> history() {
    return backupService.history();
  }

  @PostMapping("/{id}/restore")
  public BackupDTO restore(@PathVariable UUID id,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return backupService.restore(id, requesterId);
  }

  @GetMapping("/{id}/verify")
  public boolean verify(@PathVariable UUID id) {
    return backupService.verifyIntegrity(id);
  }
}
