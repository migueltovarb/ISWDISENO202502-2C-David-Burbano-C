package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Activity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, UUID> {

  Optional<Activity> findByTitle(String title);

  List<Activity> findByStartDateBetween(LocalDateTime start, LocalDateTime end);
}
