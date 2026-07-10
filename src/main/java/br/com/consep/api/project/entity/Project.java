package br.com.consep.api.project.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.consep.api.base.BaseModel;
import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.publicNotice.entity.PublicNotice;
import br.com.consep.api.shared.enums.ProjectStatus;
import br.com.consep.api.toPay.entity.ToPay;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "project")
public class Project extends BaseModel {

  @Column(name = "name")
  private String name;

  @Column(name = "value")
  private BigDecimal value;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "organization_id", referencedColumnName = "id")
  private Organization organization;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "public_notice_id", referencedColumnName = "id")
  private PublicNotice notice;

  @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<ToPay> toPay;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ProjectStatus projectStatus;
}
