package br.com.consep.api.user.dto;

import br.com.consep.api.shared.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeRoleUserDTO {
  @NotNull(message = "Cargo inválido")
  @Enumerated(EnumType.STRING)
  private Role role;
}
