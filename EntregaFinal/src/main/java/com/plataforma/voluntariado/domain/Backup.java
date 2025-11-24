package com.plataforma.voluntariado.domain;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "backups")
public class Backup {

  @Id
  private UUID backupId;

  @CreatedDate
  private Instant executedAt;
  private double sizeMB;
  private String status;
  private String frequency;
  private boolean encrypted;
}
