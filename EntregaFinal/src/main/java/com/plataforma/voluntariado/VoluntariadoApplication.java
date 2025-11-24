package com.plataforma.voluntariado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class VoluntariadoApplication {

  public static void main(String[] args) {
    SpringApplication.run(VoluntariadoApplication.class, args);
  }
}
