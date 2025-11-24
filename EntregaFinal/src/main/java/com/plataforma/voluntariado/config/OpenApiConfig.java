package com.plataforma.voluntariado.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI volunteerPlatformOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Plataforma Registro Voluntariado API")
            .description("API REST alineada al diseño de 4 capas")
            .version("1.0.0"))
        .externalDocs(new ExternalDocumentation()
            .description("Historias de usuario y diagramas en docs/")
            .url("about:blank"));
  }
}
