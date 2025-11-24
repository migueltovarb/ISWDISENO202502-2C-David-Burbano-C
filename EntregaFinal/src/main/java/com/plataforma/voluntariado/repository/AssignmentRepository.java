package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Assignment;
import com.plataforma.voluntariado.domain.enums.AssignmentStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, UUID> {

  List<Assignment> findByVolunteerId(UUID volunteerId);

  List<Assignment> findByActivityId(UUID activityId);

  long countByStatus(AssignmentStatus status);
}
