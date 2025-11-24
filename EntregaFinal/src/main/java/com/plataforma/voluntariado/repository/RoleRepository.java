package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Role;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, UUID> {

  Optional<Role> findByName(String name);
}
