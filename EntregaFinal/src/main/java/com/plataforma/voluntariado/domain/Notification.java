package com.plataforma.voluntariado.domain;

import com.plataforma.voluntariado.domain.enums.NotificationChannel;
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
@Document(collection = "notifications")
public class Notification {

  @Id
  private UUID notificationId;

  private NotificationChannel channel;
  private String type;
  private String subject;
  private String content;
  private Instant scheduledAt;
  private Instant sentAt;
  private int maxRetries;
  private int attempts;
  private boolean success;
  private UUID personId;
  private UUID activityId;
  @CreatedDate
  private Instant createdAt;
}
