package br.com.consep.api.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SummaryDocument {
  private Long id;

  private String originalName;

  // private String sha256;

  // private Date createdAt;

  // @ManyToOne
  // @JoinColumn(name = "entity_model_id")
  // private BaseModel entity;
}
