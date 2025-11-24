package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, UUID> {

  Optional<User> findByUsername(String username);
}
