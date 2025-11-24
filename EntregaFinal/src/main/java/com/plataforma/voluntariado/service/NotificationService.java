package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Assignment;
import com.plataforma.voluntariado.domain.Notification;
import com.plataforma.voluntariado.domain.enums.NotificationChannel;
import com.plataforma.voluntariado.dto.NotificationDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.NotificationRepository;
import com.plataforma.voluntariado.repository.PersonRepository;
import com.plataforma.voluntariado.service.mapper.NotificationMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;
  private final PersonRepository personRepository;
  private final AuditLogService auditLogService;
  private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  public NotificationDTO send(NotificationDTO dto, UUID requesterId) {
    Notification notification = notificationMapper.toEntity(dto);
    validateChannel(notification);
    validateEmail(notification);
    if (notification.getNotificationId() == null) {
      notification.setNotificationId(UUID.randomUUID());
    }
    int maxRetries = dto.maxRetries() != null ? dto.maxRetries() : 3;
    boolean delivered = false;
    int attempts = 0;
    while (attempts < maxRetries && !delivered) {
      attempts++;
      notification.setAttempts(attempts);
      notification.setSentAt(Instant.now());
      delivered = tryDeliver(notification);
    }
    notification.setSuccess(delivered);
    Notification saved = notificationRepository.save(notification);
    auditLogService.record("SEND_NOTIFICATION", "Notification", saved.getNotificationId(),
        requesterId, delivered, "Canal " + saved.getChannel() + " intento " + attempts, null);
    if (!delivered) {
      throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "No se pudo entregar la notificación");
    }
    return notificationMapper.toDto(saved);
  }

  public List<NotificationDTO> listByVolunteer(UUID personId) {
    return notificationRepository.findByPersonId(personId).stream()
        .map(notificationMapper::toDto)
        .toList();
  }

  public void notifyAssignment(Assignment assignment) {
    personRepository.findById(assignment.getVolunteerId()).ifPresent(person -> {
      Notification notification = Notification.builder()
          .notificationId(UUID.randomUUID())
          .personId(person.getPersonId())
          .activityId(assignment.getActivityId())
          .channel(NotificationChannel.EMAIL)
          .type("ASSIGNMENT")
          .subject("Nueva actividad asignada")
          .content("Hola " + person.getFullName() + ", tienes una nueva actividad.")
          .scheduledAt(Instant.now())
          .sentAt(Instant.now())
          .maxRetries(3)
          .attempts(1)
          .success(true)
          .build();
      notificationRepository.save(notification);
    });
  }

  private void validateChannel(Notification notification) {
    if (notification.getChannel() == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Canal de notificación requerido");
    }
  }

  private void validateEmail(Notification notification) {
    if (notification.getChannel() != NotificationChannel.EMAIL) {
      return;
    }
    personRepository.findById(notification.getPersonId()).ifPresent(person -> {
      if (!EMAIL_REGEX.matcher(person.getEmail()).matches()) {
        throw new ApiException(HttpStatus.BAD_REQUEST, "Correo del voluntario inválido");
      }
    });
  }

  private boolean tryDeliver(Notification notification) {
    // Simula entrega; en un escenario real se llamaría a un proveedor externo
    return true;
  }
}
