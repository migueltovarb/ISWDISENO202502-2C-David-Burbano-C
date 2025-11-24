package com.plataforma.voluntariado.domain;

import com.plataforma.voluntariado.domain.enums.AssignmentStatus;
import java.time.Instant;
import java.time.LocalDateTime;
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
@Document(collection = "assignments")
public class Assignment {

  @Id
  private UUID assignmentId;

  private UUID volunteerId;
  private UUID activityId;
  private AssignmentStatus status;
  @CreatedDate
  private Instant assignedAt;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private double hoursWorked;
  private double evaluationScore;
  private String cancellationReason;
  private UUID createdBy;

  public boolean isActive() {
    return status == AssignmentStatus.PENDING || status == AssignmentStatus.CONFIRMED;
  }
}
