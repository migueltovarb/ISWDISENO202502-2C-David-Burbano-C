package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Assignment;
import com.plataforma.voluntariado.domain.Attendance;
import com.plataforma.voluntariado.dto.AttendanceDTO;
import com.plataforma.voluntariado.dto.ConfirmAttendanceDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.AssignmentRepository;
import com.plataforma.voluntariado.repository.ActivityRepository;
import com.plataforma.voluntariado.repository.AttendanceRepository;
import com.plataforma.voluntariado.repository.PersonRepository;
import com.plataforma.voluntariado.service.mapper.AttendanceMapper;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final AttendanceRepository attendanceRepository;
  private final AssignmentRepository assignmentRepository;
  private final PersonRepository personRepository;
  private final ActivityRepository activityRepository;
  private final AttendanceMapper attendanceMapper;
  private final AuditLogService auditLogService;

  @Transactional
  public AttendanceDTO registerAttendance(AttendanceDTO dto, UUID requesterId) {
    Assignment assignment = assignmentRepository.findById(UUID.fromString(dto.assignmentId()))
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Asignación no encontrada"));
    validateDate(dto.date(), assignment);
    validateTimes(dto);

    Attendance attendance = attendanceMapper.toEntity(dto);
    attendance.setAttendanceId(UUID.randomUUID());
    double hours = Duration.between(dto.arrivalTime(), dto.departureTime()).toMinutes() / 60.0;
    attendance.setTotalHours(hours);
    Attendance saved = attendanceRepository.save(attendance);

    assignment.setHoursWorked(assignment.getHoursWorked() + hours);
    assignmentRepository.save(assignment);
    personRepository.findById(assignment.getVolunteerId()).ifPresent(p -> {
      p.addHours((int) Math.round(hours));
      personRepository.save(p);
    });

    auditLogService.record("ATTENDANCE", "Attendance", saved.getAttendanceId(), requesterId, true,
        "Registro de asistencia", null);
    return attendanceMapper.toDto(saved);
  }

  public boolean confirmDigital(ConfirmAttendanceDTO dto) {
    Assignment assignment = assignmentRepository.findById(UUID.fromString(dto.assignmentId()))
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Asignación no encontrada"));
    if (assignment.getStartDate() != null && assignment.getEndDate() != null &&
        Instant.now().isAfter(assignment.getEndDate().toInstant(ZoneOffset.UTC))) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Confirmación solo durante la actividad");
    }
    auditLogService.record("ATTENDANCE_CONFIRM", "Assignment", assignment.getAssignmentId(),
        assignment.getVolunteerId(), true, "Confirmación código " + dto.code(), null);
    return true;
  }

  private void validateDate(LocalDate date, Assignment assignment) {
    LocalDateTime start = assignment.getStartDate();
    LocalDateTime end = assignment.getEndDate();
    if (start == null || end == null) {
      return;
    }
    if (date.isBefore(start.toLocalDate()) || date.isAfter(end.toLocalDate())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Fecha fuera del rango de la actividad");
    }
  }

  private void validateTimes(AttendanceDTO dto) {
    if (dto.arrivalTime().isAfter(dto.departureTime())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "La hora de salida debe ser posterior a la de llegada");
    }
    assignmentRepository.findById(UUID.fromString(dto.assignmentId())).ifPresent(assignment -> {
      activityRepository.findById(assignment.getActivityId()).ifPresent(activity -> {
        LocalDateTime start = activity.getStartDate();
        LocalDateTime end = activity.getEndDate();
        LocalDateTime arrival = LocalDateTime.of(dto.date(), dto.arrivalTime());
        LocalDateTime departure = LocalDateTime.of(dto.date(), dto.departureTime());
        if (arrival.isBefore(start) || departure.isAfter(end)) {
          throw new ApiException(HttpStatus.BAD_REQUEST, "La asistencia debe registrarse dentro del horario de la actividad");
        }
      });
    });
  }
}
