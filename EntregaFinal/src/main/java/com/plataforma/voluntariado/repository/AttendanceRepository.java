package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Attendance;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttendanceRepository extends MongoRepository<Attendance, UUID> {

  List<Attendance> findByAssignmentId(UUID assignmentId);

  List<Attendance> findByDate(LocalDate date);
}
