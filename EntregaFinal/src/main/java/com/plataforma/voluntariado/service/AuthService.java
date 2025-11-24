package com.plataforma.voluntariado.service;

import com.plataforma.voluntariado.domain.User;
import com.plataforma.voluntariado.dto.AuthResponse;
import com.plataforma.voluntariado.dto.LoginRequest;
import com.plataforma.voluntariado.dto.RecoveryRequestDTO;
import com.plataforma.voluntariado.dto.UserDTO;
import com.plataforma.voluntariado.exception.ApiException;
import com.plataforma.voluntariado.repository.UserRepository;
import com.plataforma.voluntariado.security.JwtProperties;
import com.plataforma.voluntariado.security.JwtTokenProvider;
import com.plataforma.voluntariado.security.UserPrincipal;
import com.plataforma.voluntariado.service.mapper.UserMapper;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

  private static final int MAX_ATTEMPTS = 5;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;
  private final JwtProperties jwtProperties;
  private final UserMapper userMapper;
  private final AuditLogService auditLogService;

  public AuthResponse login(LoginRequest request, String ipAddress) {
    var user = userRepository.findByUsername(request.username())
        .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

    if (user.isLocked()) {
      throw new ApiException(HttpStatus.UNAUTHORIZED, "Cuenta bloqueada temporalmente");
    }

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      user.incrementFailedAttempts(MAX_ATTEMPTS);
      userRepository.save(user);
      auditLogService.record("LOGIN_FAIL", "User", user.getId(), user.getId(), false,
          "Intento fallido", ipAddress);
      throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
    }

    user.resetAttempts();
    user.setLastAccess(Instant.now());
    user.setActiveSession(true);
    userRepository.save(user);

    var principal = new UserPrincipal(user);
    String token = tokenProvider.generateToken(principal);
    Instant expiresAt = Instant.now().plusSeconds((long) jwtProperties.getExpirationMinutes() * 60);
    auditLogService.record("LOGIN_SUCCESS", "User", user.getId(), user.getId(), true,
        "Ingreso exitoso", ipAddress);
    return new AuthResponse(token, expiresAt, user.getRoles());
  }

  public void recoverPassword(RecoveryRequestDTO request) {
    userRepository.findByUsername(request.email()).ifPresent(user -> {
      auditLogService.record("RECOVER_PASSWORD", "User", user.getId(), user.getId(), true,
          "Solicitud de recuperación", null);
    });
  }

  public User register(UserDTO dto) {
    if (!StringUtils.hasText(dto.password())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
    }
    if (!isValidPassword(dto.password())) {
      throw new ApiException(HttpStatus.BAD_REQUEST,
          "Contraseña insegura: mínimo 8 caracteres, mayúscula, número y símbolo");
    }
    userRepository.findByUsername(dto.username())
        .ifPresent(u -> {
          throw new ApiException(HttpStatus.CONFLICT, "El usuario ya existe");
        });
    User entity = userMapper.toEntity(dto);
    entity.setPasswordHash(passwordEncoder.encode(dto.password()));
    entity.setSessionTimeoutMin(30);
    entity.setActiveSession(false);
    entity.setLastAccess(Instant.now());
    if (entity.getId() == null) {
      entity.setId(UUID.randomUUID());
    }
    return userRepository.save(entity);
  }

  private boolean isValidPassword(String password) {
    return password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-]).{8,}$");
  }

  public void logout(String ipAddress) {
    userRepository.findAll().stream()
        .filter(User::isActiveSession)
        .forEach(u -> {
          u.setActiveSession(false);
          userRepository.save(u);
          auditLogService.record("LOGOUT", "User", u.getId(), u.getId(), true,
              "Cierre de sesión", ipAddress);
        });
  }
}
