package br.com.consep.api.toPay.dto;

import br.com.consep.api.shared.enums.ToPayStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateStatusToPay {
  @NotNull(message = "Status inválido")
  @Enumerated(EnumType.STRING)
  private ToPayStatus status;
}
