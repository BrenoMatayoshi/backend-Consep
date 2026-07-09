package br.com.consep.api.organization.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestOrganization {

  // private Long id;

  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotBlank(message = "Abbreviation cannot be blank")
  private String abbreviation;

  // private List<PublicNotice> notices;

  // private List<Project> projects;

  // private List<User> users;
}
