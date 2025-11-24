package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.AuthResponse;
import com.plataforma.voluntariado.dto.LoginRequest;
import com.plataforma.voluntariado.dto.RecoveryRequestDTO;
import com.plataforma.voluntariado.dto.UserDTO;
import com.plataforma.voluntariado.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest request, HttpServletRequest http) {
    return authService.login(request, http.getRemoteAddr());
  }

  @PostMapping("/recover")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void recoverPassword(@Valid @RequestBody RecoveryRequestDTO request) {
    authService.recoverPassword(request);
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@Valid @RequestBody UserDTO dto) {
    authService.register(dto);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(HttpServletRequest http) {
    authService.logout(http.getRemoteAddr());
  }
}
