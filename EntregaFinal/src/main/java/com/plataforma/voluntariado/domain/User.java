package com.plataforma.voluntariado.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

  @Id
  private UUID id;

  @Indexed(unique = true)
  private String username;
  private String passwordHash;
  private int failedAttempts;
  private Instant lockedUntil;
  private Instant lastAccess;
  private int sessionTimeoutMin;
  private boolean activeSession;
  private UUID personId;
  @Default
  private Set<String> roles = new HashSet<>();
  @CreatedDate
  private Instant createdAt;

  public boolean isLocked() {
    return lockedUntil != null && lockedUntil.isAfter(Instant.now());
  }

  public void lockForMinutes(int minutes) {
    this.lockedUntil = Instant.now().plusSeconds(minutes * 60L);
  }

  public void incrementFailedAttempts(int maxAttempts) {
    this.failedAttempts += 1;
    if (this.failedAttempts >= maxAttempts) {
      lockForMinutes(15);
    }
  }

  public void resetAttempts() {
    this.failedAttempts = 0;
    this.lockedUntil = null;
  }
}
