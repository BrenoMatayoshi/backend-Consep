package br.com.consep.api.payment.mapper;

import br.com.consep.api.document.mapper.DocumentMapper;
import br.com.consep.api.payment.dto.RequestPayment;
import br.com.consep.api.payment.dto.SummaryPayment;
import br.com.consep.api.payment.entity.Payment;

public class PaymentMapper {
  public static SummaryPayment toSummary(Payment request) {
    if (request == null) {
      return null;
    }

    SummaryPayment response = new SummaryPayment();

    response.setId(request.getId());
    response.setTypeDocument(request.getTypeDocument());
    response.setValue(request.getValue());
    // response.setDocument(DocumentMapper.toSummary(request.getDocument()));

    return response;
  }

  public static Payment toEntity(RequestPayment request) {
    if (request == null) {
      return null;
    }
    Payment payment = new Payment();

    payment.setTypeDocument(request.getTypeDocument());

    return payment;
  }
}
