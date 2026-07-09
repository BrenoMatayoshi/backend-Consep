package br.com.consep.api.project.dto;

import java.math.BigDecimal;

import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.shared.enums.ProjectStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MinimalProject {
  private Long id;
  private String name;
  private BigDecimal value;
  private ProjectStatus status;
  private SummaryOrganization organization;
}
