package com.plataforma.voluntariado.domain;

import java.time.Instant;
import java.time.LocalDate;
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
@Document(collection = "persons")
public class Person {

  @Id
  private UUID personId;

  @Indexed(unique = true)
  private String email;
  private String fullName;
  private String phone;
  private LocalDate birthDate;
  private String address;
  private String documentId;
  @CreatedDate
  private Instant registrationDate;
  private String roleType;
  private int totalHours;
  private int totalAssignments;
  private double performanceScore;
  private String notificationPreferences;
  private boolean active;
  private Instant lastProfileUpdate;

  public void addHours(int hours) {
    this.totalHours += Math.max(hours, 0);
  }

  public void addAssignment() {
    this.totalAssignments += 1;
  }

  public void deactivate() {
    this.active = false;
  }

  public void touchProfileUpdate() {
    this.lastProfileUpdate = Instant.now();
  }
}
