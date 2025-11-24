package com.plataforma.voluntariado.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "attendance")
public class Attendance {

  @Id
  private UUID attendanceId;

  private UUID assignmentId;
  private LocalDate date;
  private boolean present;
  private LocalTime arrivalTime;
  private LocalTime departureTime;
  private double totalHours;
  private String performanceLevel;
  private String observations;
}
