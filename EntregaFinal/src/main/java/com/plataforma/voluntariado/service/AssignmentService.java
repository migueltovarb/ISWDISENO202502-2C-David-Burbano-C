package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Assignment;
import com.plataforma.voluntariado.domain.enums.AssignmentStatus;
import com.plataforma.voluntariado.dto.AssignmentDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.ActivityRepository;
import com.plataforma.voluntariado.repository.AssignmentRepository;
import com.plataforma.voluntariado.repository.PersonRepository;
import com.plataforma.voluntariado.service.mapper.AssignmentMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final ActivityRepository activityRepository;
  private final PersonRepository personRepository;
  private final AssignmentMapper assignmentMapper;
  private final AuditLogService auditLogService;
  private final NotificationService notificationService;

  @Transactional
  public AssignmentDTO assignVolunteer(AssignmentDTO dto, UUID requesterId) {
    var volunteerId = UUID.fromString(dto.volunteerId());
    var activityId = UUID.fromString(dto.activityId());
    personRepository.findById(volunteerId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Voluntario no existe"));
    var activity = activityRepository.findById(activityId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Actividad no existe"));

    validateDates(dto.startDate(), dto.endDate());
    if (!activity.hasAvailability()) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "La actividad no tiene cupos");
    }

    assignmentRepository.findByVolunteerId(volunteerId).stream()
        .filter(a -> a.getActivityId().equals(activityId))
        .findFirst()
        .ifPresent(a -> {
          throw new ApiException(HttpStatus.CONFLICT, "Ya existe una asignación para este voluntario");
        });

    Assignment assignment = assignmentMapper.toEntity(dto);
    assignment.setAssignmentId(UUID.randomUUID());
    assignment.setStatus(AssignmentStatus.PENDING);
    assignment.setVolunteerId(volunteerId);
    assignment.setActivityId(activityId);
    Assignment saved = assignmentRepository.save(assignment);

    activity.setAssignedVolunteers(activity.getAssignedVolunteers() + 1);
    activityRepository.save(activity);

    auditLogService.record("ASSIGN_VOLUNTEER", "Assignment", saved.getAssignmentId(), requesterId,
        true, "Asignación creada", null);
    notificationService.notifyAssignment(saved);
    return assignmentMapper.toDto(saved);
  }

  @Transactional
  public AssignmentDTO cancelAssignment(UUID assignmentId, String reason, UUID requesterId) {
    Assignment assignment = assignmentRepository.findById(assignmentId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Asignación no encontrada"));
    boolean wasActive = assignment.isActive();
    if (assignment.getStartDate() != null &&
        assignment.getStartDate().isBefore(java.time.LocalDateTime.now().plusHours(24)) &&
        (reason == null || reason.isBlank())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Se requiere motivo para cancelaciones a menos de 24h");
    }
    assignment.setStatus(AssignmentStatus.CANCELLED);
    assignment.setCancellationReason(reason);
    Assignment saved = assignmentRepository.save(assignment);
    activityRepository.findById(assignment.getActivityId()).ifPresent(activity -> {
      if (wasActive && activity.getAssignedVolunteers() > 0) {
        activity.setAssignedVolunteers(activity.getAssignedVolunteers() - 1);
        activityRepository.save(activity);
      }
    });
    auditLogService.record("CANCEL_ASSIGNMENT", "Assignment", assignmentId, requesterId, true,
        "Cancelación: " + reason, null);
    return assignmentMapper.toDto(saved);
  }

  private void validateDates(java.time.LocalDateTime start, java.time.LocalDateTime end) {
    if (start.isAfter(end)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "La fecha de fin debe ser posterior a inicio");
    }
    if (start.isBefore(java.time.LocalDateTime.now().plusHours(24))) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Asignaciones deben programarse con 24h de anticipación");
    }
  }
}
