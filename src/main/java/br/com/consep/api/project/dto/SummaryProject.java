package br.com.consep.api.project.dto;

import java.math.BigDecimal;

import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.shared.enums.ProjectStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SummaryProject {
  private Long id;
  private String name;
  private BigDecimal value;
  @Enumerated(EnumType.STRING)
  private ProjectStatus status;
  private String noticeNumber;
  private SummaryDocument document;
  private SummaryOrganization organization;
}
