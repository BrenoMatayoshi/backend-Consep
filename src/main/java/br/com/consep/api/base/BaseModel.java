package br.com.consep.api.base;

import br.com.consep.api.document.entity.Document;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "entity_model")
@Entity
public abstract class BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "entity", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Document document;
}
