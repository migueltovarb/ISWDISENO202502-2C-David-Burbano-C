package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Person;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, UUID> {

  Optional<Person> findByEmail(String email);
}
