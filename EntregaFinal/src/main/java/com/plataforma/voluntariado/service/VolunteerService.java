package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.Person;
import com.plataforma.voluntariado.dto.ActivityDTO;
import com.plataforma.voluntariado.dto.VolunteerDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.ActivityRepository;
import com.plataforma.voluntariado.repository.AssignmentRepository;
import com.plataforma.voluntariado.repository.PersonRepository;
import com.plataforma.voluntariado.service.mapper.ActivityMapper;
import com.plataforma.voluntariado.service.mapper.VolunteerMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class VolunteerService {

  private final PersonRepository personRepository;
  private final AssignmentRepository assignmentRepository;
  private final ActivityRepository activityRepository;
  private final VolunteerMapper volunteerMapper;
  private final ActivityMapper activityMapper;
  private final AuditLogService auditLogService;

  public VolunteerDTO getProfile(UUID id) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Voluntario no encontrado"));
    return volunteerMapper.toDto(person);
  }

  @Transactional
  public VolunteerDTO registerVolunteer(VolunteerDTO dto, UUID requesterId) {
    personRepository.findByEmail(dto.email()).ifPresent(p -> {
      throw new ApiException(HttpStatus.CONFLICT, "Ya existe un voluntario con ese correo");
    });
    if (!dto.email().contains("@")) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Correo inválido");
    }
    Person entity = volunteerMapper.toEntity(dto);
    entity.setPersonId(UUID.randomUUID());
    entity.setActive(true);
    entity.setRegistrationDate(Instant.now());
    entity.setLastProfileUpdate(Instant.now());
    Person saved = personRepository.save(entity);
    auditLogService.record("REGISTER_VOLUNTEER", "Person", saved.getPersonId(), requesterId, true,
        "Registro inicial", null);
    return volunteerMapper.toDto(saved);
  }

  @Transactional
  public VolunteerDTO updateProfile(UUID id, VolunteerDTO dto, UUID requesterId) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Voluntario no encontrado"));
    if (StringUtils.hasText(dto.email()) && !dto.email().equalsIgnoreCase(person.getEmail())) {
      personRepository.findByEmail(dto.email()).ifPresent(existing -> {
        throw new ApiException(HttpStatus.CONFLICT, "El correo ya está en uso");
      });
      person.setEmail(dto.email());
    }
    person.setFullName(dto.fullName());
    person.setPhone(dto.phone());
    person.setBirthDate(dto.birthDate());
    person.setAddress(dto.address());
    person.setNotificationPreferences(dto.notificationPreferences());
    person.setLastProfileUpdate(Instant.now());
    personRepository.save(person);
    auditLogService.record("UPDATE_PROFILE", "Person", id, requesterId, true,
        "Actualización de perfil", null);
    return volunteerMapper.toDto(person);
  }

  public List<ActivityDTO> getCalendar(UUID volunteerId) {
    var assignments = assignmentRepository.findByVolunteerId(volunteerId);
    var activities = assignments.stream()
        .map(ass -> activityRepository.findById(ass.getActivityId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
    return activities.stream().map(activityMapper::toDto).collect(Collectors.toList());
  }
}
