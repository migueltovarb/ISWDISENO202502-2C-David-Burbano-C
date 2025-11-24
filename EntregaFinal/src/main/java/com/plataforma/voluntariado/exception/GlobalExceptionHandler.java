package com.plataforma.voluntariado.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Map<String, Object>> handleApi(ApiException ex) {
    return ResponseEntity.status(ex.getStatus())
        .body(Map.of(
            "timestamp", Instant.now(),
            "error", ex.getStatus().getReasonPhrase(),
            "message", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    var errors = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage())
        .toList();
    return ResponseEntity.badRequest()
        .body(Map.of(
            "timestamp", Instant.now(),
            "error", "Validation failed",
            "details", errors));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of(
            "timestamp", Instant.now(),
            "error", "Unexpected error",
            "message", ex.getMessage()));
  }
}
