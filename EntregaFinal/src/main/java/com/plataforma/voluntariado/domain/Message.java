package com.plataforma.voluntariado.domain;

import com.plataforma.voluntariado.domain.enums.MessageType;
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
@Document(collection = "messages")
public class Message {

  @Id
  private UUID messageId;

  private UUID activityId;
  private UUID senderId;
  private UUID receiverId;
  private MessageType messageType;
  private String content;
  @CreatedDate
  private Instant createdAt;
  private boolean read;
}
