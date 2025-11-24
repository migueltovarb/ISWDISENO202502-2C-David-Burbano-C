package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Document;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<Document, UUID> {

  List<Document> findByActivityId(UUID activityId);

  List<Document> findByPersonId(UUID personId);
}
