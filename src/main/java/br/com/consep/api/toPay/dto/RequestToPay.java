package br.com.consep.api.toPay.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.consep.api.shared.enums.TypePayment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestToPay {

  // private Long id;

  @NotNull(message = "O valor do boleto não pode ser nulo")
  @DecimalMin(value = "0.00", message = "O valor não pode ser negativo")
  @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos e 2 casas decimais")
  private BigDecimal value;

  @Enumerated(EnumType.STRING)
  private TypePayment typePayment;

  // private String pixCode;

  // private LocalDate createdAt;

  // private Date paidAt;

  @NotNull(message = "A data de vencimento não pode ser nula")
  // @FutureOrPresent(message = "A data não pode estar no passado")
  private LocalDate dueDate;

  // private Date scheduledAt;

  // @Enumerated(EnumType.STRING)
  // private Status status;

  // private Payment payment;

  // private Project project;

}
