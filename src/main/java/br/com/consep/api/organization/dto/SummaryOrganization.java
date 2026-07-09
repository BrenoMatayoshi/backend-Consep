package br.com.consep.api.organization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryOrganization {
  private Long id;

  private String name;

  private String abbreviation;

  private int usersCount;

  private int projectsCount;

  private int noticesCount;

  // private List<PublicNotice> notices;

  // private List<Project> projects;

  // private List<User> users;
}
