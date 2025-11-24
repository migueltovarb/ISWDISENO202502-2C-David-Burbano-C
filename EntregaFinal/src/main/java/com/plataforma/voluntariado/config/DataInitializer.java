package com.plataforma.voluntariado.config;

import com.plataforma.voluntariado.domain.Role;
import com.plataforma.voluntariado.domain.User;
import com.plataforma.voluntariado.repository.RoleRepository;
import com.plataforma.voluntariado.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Environment environment;

  @Bean
  CommandLineRunner seed() {
    return args -> {
      // Only seed data if not in test profile
      if (!List.of(environment.getActiveProfiles()).contains("test")) {
        seedRoles();
        seedAdmin();
      }
    };
  }

  private void seedRoles() {
    List<Role> defaults = List.of(
        Role.builder().id(UUID.randomUUID()).name("ADMIN").description("Administrador").permissions(List.of("ALL")).isDefault(false).build(),
        Role.builder().id(UUID.randomUUID()).name("COORDINATOR").description("Coordinador").permissions(List.of("ACTIVITY_WRITE", "ASSIGNMENT_WRITE")).isDefault(true).build(),
        Role.builder().id(UUID.randomUUID()).name("VOLUNTEER").description("Voluntario").permissions(List.of("SELF_SERVICE")).isDefault(true).build(),
        Role.builder().id(UUID.randomUUID()).name("AUDITOR").description("Auditor").permissions(List.of("READ_ONLY")).isDefault(false).build()
    );
    defaults.forEach(role -> roleRepository.findByName(role.getName()).orElseGet(() -> roleRepository.save(role)));
  }

  private void seedAdmin() {
    userRepository.findByUsername("admin").orElseGet(() -> {
      User admin = User.builder()
          .id(UUID.randomUUID())
          .username("admin")
          .passwordHash(passwordEncoder.encode("Admin123*"))
          .failedAttempts(0)
          .sessionTimeoutMin(30)
          .lastAccess(Instant.now())
          .activeSession(false)
          .roles(Set.of("ADMIN"))
          .build();
      return userRepository.save(admin);
    });
  }
}
