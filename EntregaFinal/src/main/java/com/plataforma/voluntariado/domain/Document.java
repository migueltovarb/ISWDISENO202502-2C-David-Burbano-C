package com.plataforma.voluntariado.domain;

import com.plataforma.voluntariado.domain.enums.DocumentType;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
public class Document {

  @Id
  private UUID documentId;

  private DocumentType documentType;
  private UUID activityId;
  private UUID personId;
  private String title;
  private String storagePath;
  private long sizeBytes;
  @CreatedDate
  private Instant uploadedAt;
  private boolean verified;
}
