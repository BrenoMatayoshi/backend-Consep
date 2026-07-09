package br.com.consep.api.project.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestProject {

  // private Long id;

  @NotBlank(message = "O projeto deve conter um nome")
  private String name;

  @NotNull(message = "O valor do projeto não pode ser nulo")
  @DecimalMin(value = "0.00", message = "O valor não pode ser negativo")
  @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos e 2 casas decimais")
  private BigDecimal value;

  // @JsonIgnore
  // private Organization organization;

  // @JsonIgnore
  // private PublicNotice notice;

  // private List<ToPay> toPay;
}
