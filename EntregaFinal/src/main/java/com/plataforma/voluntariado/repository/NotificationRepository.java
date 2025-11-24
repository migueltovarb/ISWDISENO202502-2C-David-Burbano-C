package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Notification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, UUID> {

  List<Notification> findByPersonId(UUID personId);
}
