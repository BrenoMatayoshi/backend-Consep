package br.com.consep.api.organization.dto;

import java.util.List;

import br.com.consep.api.project.dto.SummaryProject;
import br.com.consep.api.publicNotice.dto.SummaryPublicNotice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DetailedOrganization {
  private Long id;
  private String name;
  private String abbreviation;
  private List<SummaryPublicNotice> notices;
  private List<SummaryProject> projects;
}