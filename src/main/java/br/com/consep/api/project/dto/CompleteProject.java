package br.com.consep.api.project.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.publicNotice.dto.SummaryPublicNotice;
import br.com.consep.api.shared.enums.ProjectStatus;
import br.com.consep.api.toPay.dto.SummaryToPay;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompleteProject {
  private Long id;

  private String name;

  private BigDecimal value;

  private SummaryOrganization organization;

  private SummaryPublicNotice notice;

  private List<SummaryToPay> toPay;

  @Enumerated(EnumType.STRING)
  private ProjectStatus projectStatus;

  private BigDecimal valueUsed;

  private SummaryDocument document;
}
