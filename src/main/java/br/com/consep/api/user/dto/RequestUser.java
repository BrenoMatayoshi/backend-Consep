package br.com.consep.api.user.dto;

import br.com.consep.api.shared.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestUser {

  // private Long id;

  private String name;

  @NotBlank(message = "Password cannot be blank")
  private String password;

  @NotBlank(message = "Login cannot be blank")
  private String login;

  @Enumerated(EnumType.STRING)
  private Role role;

  // @JsonIgnore
  // private RequestOrganization organization;
}
