package com.plataforma.voluntariado.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "activities")
public class Activity {

  @Id
  private UUID activityId;

  @Indexed(unique = true)
  private String title;
  private String description;
  private String type;
  private String location;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String status;
  private int requiredVolunteers;
  private int assignedVolunteers;
  private UUID coordinatorId;
  private boolean active;
  private UUID createdBy;
  @CreatedDate
  private Instant createdAt;
  @LastModifiedDate
  private Instant updatedAt;

  public boolean hasAvailability() {
    return assignedVolunteers < requiredVolunteers;
  }
}
