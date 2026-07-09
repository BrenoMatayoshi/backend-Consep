package br.com.consep.api.document.entity;

import java.util.UUID;

import br.com.consep.api.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "document")
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "original_name")
  private String originalName;

  @Column(name = "generated_name", columnDefinition = "CHAR(36)", nullable = false)
  private UUID generatedName;

  @Column(name = "sha256")
  private String sha256;

  @OneToOne
  @JoinColumn(name = "entity_model_id")
  private BaseModel entity;
}
