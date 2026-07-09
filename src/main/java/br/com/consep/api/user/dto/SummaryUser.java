package br.com.consep.api.user.dto;

import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.shared.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryUser {

  private Long id;

  private String name;

  // private String password;

  // private String login;

  @Enumerated(EnumType.STRING)
  private Role role;

  private SummaryOrganization organization;
}
