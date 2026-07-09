package br.com.consep.api.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.shared.enums.TypeDocument;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SummaryPayment {

  private Long id;

  @Enumerated(EnumType.STRING)
  private TypeDocument typeDocument;

  private LocalDate createdAt;

  private BigDecimal value;

  // private SummaryDocument document;
}
