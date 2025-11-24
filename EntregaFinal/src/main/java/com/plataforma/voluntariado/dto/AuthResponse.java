package com.plataforma.voluntariado.dto;

import java.time.Instant;
import java.util.Set;

public record AuthResponse(String token, Instant expiresAt, Set<String> roles) {
}
