package com.plataforma.voluntariado.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final JwtProperties properties;
  private Key key;

  public JwtTokenProvider(JwtProperties properties) {
    this.properties = properties;
  }

  @PostConstruct
  void init() {
    this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
  }

  public String generateToken(UserPrincipal user) {
    Instant now = Instant.now();
    Instant expiry = now.plusSeconds((long) properties.getExpirationMinutes() * 60);
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiry))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }
}
