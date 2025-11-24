package com.plataforma.voluntariado.domain;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {

  @Id
  private UUID id;

  @Indexed(unique = true)
  private String name;
  private String description;
  private List<String> permissions;
  private boolean isDefault;
}
