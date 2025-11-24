package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, UUID> {

  List<Message> findByActivityId(UUID activityId);
}
