package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.DocumentDTO;
import com.plataforma.voluntariado.service.DocumentService;
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
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

  @PostMapping
  public DocumentDTO upload(@Valid @RequestBody DocumentDTO dto) {
    return documentService.upload(dto);
  }

  @GetMapping("/activity/{activityId}")
  public List<DocumentDTO> listByActivity(@PathVariable UUID activityId) {
    return documentService.listByActivity(activityId);
  }
}
