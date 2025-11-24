package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Document;
import com.plataforma.voluntariado.domain.enums.DocumentType;
import com.plataforma.voluntariado.dto.DocumentDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.DocumentRepository;
import com.plataforma.voluntariado.service.mapper.DocumentMapper;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentService {

  private final DocumentRepository documentRepository;
  private final DocumentMapper documentMapper;
  private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "pdf", "docx", "xlsx");

  @Transactional
  public DocumentDTO upload(DocumentDTO dto) {
    validateDocument(dto);
    Document document = documentMapper.toEntity(dto);
    if (document.getDocumentId() == null) {
      document.setDocumentId(UUID.randomUUID());
    }
    document.setUploadedAt(Instant.now());
    Document saved = documentRepository.save(document);
    return documentMapper.toDto(saved);
  }

  public List<DocumentDTO> listByActivity(UUID activityId) {
    return documentRepository.findByActivityId(activityId).stream()
        .map(documentMapper::toDto)
        .toList();
  }

  private void validateDocument(DocumentDTO dto) {
    if (dto.sizeBytes() > 5 * 1024 * 1024) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "El archivo supera 5MB");
    }
    if (dto.sizeBytes() <= 0) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "El tamaño del archivo es inválido");
    }
    if (dto.activityId() != null) {
      long count = documentRepository.findByActivityId(UUID.fromString(dto.activityId())).size();
      if (count >= 10) {
        throw new ApiException(HttpStatus.BAD_REQUEST, "Límite de 10 evidencias por actividad alcanzado");
      }
    }
    if (isMalicious(dto.storagePath())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Archivo potencialmente malicioso");
    }
    String extension = getExtension(dto.storagePath());
    if (!ALLOWED_EXTENSIONS.contains(extension)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Tipo de archivo no permitido");
    }
    if (dto.documentType() == DocumentType.OTHER && extension.equals("exe")) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Tipo de archivo no permitido");
    }
  }

  private boolean isMalicious(String path) {
    String lower = path.toLowerCase();
    return lower.endsWith(".exe") || lower.endsWith(".bat") || lower.endsWith(".js");
  }

  private String getExtension(String path) {
    int idx = path.lastIndexOf('.');
    if (idx == -1 || idx == path.length() - 1) {
      return "";
    }
    return path.substring(idx + 1).toLowerCase();
  }
}
