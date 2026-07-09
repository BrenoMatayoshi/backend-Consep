package br.com.consep.api.publicNotice.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.consep.api.base.BaseModel;
import br.com.consep.api.organization.entity.Organization;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "public_notice")
public class PublicNotice extends BaseModel {
  @Column(name = "number")
  private String number;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "end_at")
  private LocalDate endAt;

  @ManyToOne(optional = false)
  @JoinColumn(name = "organization_id", referencedColumnName = "id")
  @JsonIgnore
  private Organization organization;

  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDate.now();
    }
  }
}
