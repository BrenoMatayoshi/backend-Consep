package br.com.consep.api.toPay.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.payment.dto.SummaryPayment;
import br.com.consep.api.project.dto.NameAndIdProjectDTO;
import br.com.consep.api.shared.enums.ToPayStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryToPay {

  private Long id;

  // private String sha256;

  private BigDecimal value;

  // @Enumerated(EnumType.STRING)
  // private TypePayment typePayment;

  // private Date paidAt;

  private LocalDate dueDate;

  // private Date scheduledAt;

  @Enumerated(EnumType.STRING)
  private ToPayStatus status;

  private List<SummaryPayment> payment;

  private SummaryDocument document;

  private NameAndIdProjectDTO project;

  // private Project project;
}
