package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Activity;
import com.plataforma.voluntariado.dto.ActivityDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.ActivityRepository;
import com.plataforma.voluntariado.service.mapper.ActivityMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityService {

  private final ActivityRepository activityRepository;
  private final ActivityMapper activityMapper;
  private final AuditLogService auditLogService;

  @Transactional
  public ActivityDTO create(ActivityDTO dto, UUID requesterId) {
    validateDates(dto.startDate(), dto.endDate());
    activityRepository.findByTitle(dto.title()).ifPresent(existing -> {
      throw new ApiException(HttpStatus.CONFLICT, "La actividad ya existe");
    });
    Activity entity = activityMapper.toEntity(dto);
    entity.setActivityId(UUID.randomUUID());
    entity.setAssignedVolunteers(0);
    entity.setActive(true);
    entity.setCreatedBy(requesterId);
    Activity saved = activityRepository.save(entity);
    auditLogService.record("CREATE_ACTIVITY", "Activity", saved.getActivityId(), requesterId, true,
        "Alta de actividad", null);
    return activityMapper.toDto(saved);
  }

  @Transactional
  public ActivityDTO update(UUID id, ActivityDTO dto, UUID requesterId) {
    validateDates(dto.startDate(), dto.endDate());
    Activity activity = activityRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
    activity.setDescription(dto.description());
    activity.setType(dto.type());
    activity.setLocation(dto.location());
    activity.setStartDate(dto.startDate());
    activity.setEndDate(dto.endDate());
    activity.setStatus(dto.status());
    activity.setRequiredVolunteers(dto.requiredVolunteers());
    if (dto.coordinatorId() != null && !dto.coordinatorId().isBlank()) {
      activity.setCoordinatorId(UUID.fromString(dto.coordinatorId()));
    }
    activity.setActive(dto.active());
    Activity saved = activityRepository.save(activity);
    auditLogService.record("UPDATE_ACTIVITY", "Activity", id, requesterId, true,
        "Actualización de actividad", null);
    return activityMapper.toDto(saved);
  }

  public List<ActivityDTO> upcoming(LocalDateTime from, LocalDateTime to) {
    return activityRepository.findByStartDateBetween(from, to).stream()
        .map(activityMapper::toDto)
        .toList();
  }

  private void validateDates(LocalDateTime start, LocalDateTime end) {
    if (start.isAfter(end)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "La fecha de fin debe ser posterior al inicio");
    }
    if (start.isBefore(LocalDateTime.now().plusHours(24))) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "La actividad debe crearse con al menos 24h de anticipación");
    }
  }
}
