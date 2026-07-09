package br.com.consep.api.project.dto;

import br.com.consep.api.shared.enums.ProjectStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateStatusProject {
  @NotNull(message = "Status inválido")
  @Enumerated(EnumType.STRING)
  private ProjectStatus status;
}
