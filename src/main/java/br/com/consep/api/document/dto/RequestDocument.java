package br.com.consep.api.document.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestDocument {

  // private Long id;

  // private String originalName;

  @NotBlank(message = "Erro de integridade model")
  private String sha256;

  // private Date createdAt;

  // @ManyToOne
  // @JoinColumn(name = "entity_model_id")
  // private BaseModel entity;
}
