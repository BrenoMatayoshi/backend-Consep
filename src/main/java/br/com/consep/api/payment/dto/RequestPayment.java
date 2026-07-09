package br.com.consep.api.payment.dto;

import br.com.consep.api.shared.enums.TypeDocument;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class RequestPayment {
  @NotNull(message = "Tipo inválido")
  @Enumerated(EnumType.STRING)
  private TypeDocument typeDocument;
}
