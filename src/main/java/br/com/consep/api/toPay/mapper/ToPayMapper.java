package br.com.consep.api.toPay.mapper;

import br.com.consep.api.document.mapper.DocumentMapper;
import br.com.consep.api.payment.mapper.PaymentMapper;
import br.com.consep.api.project.mapper.ProjectMapper;
import br.com.consep.api.toPay.dto.RequestToPay;
import br.com.consep.api.toPay.dto.SummaryToPay;
import br.com.consep.api.toPay.entity.ToPay;

public class ToPayMapper {
  public static SummaryToPay toSummary(ToPay entity) {
    if (entity == null) {
      return null;
    }

    SummaryToPay response = new SummaryToPay();

    response.setId(entity.getId());
    response.setStatus(entity.getStatus());
    // response.setTypePayment(entity.getTypePayment());
    response.setValue(entity.getValue());
    if (entity.getPayment() != null) {
      response.setPayment(entity.getPayment().stream().map(PaymentMapper::toSummary).toList());
    }
    response.setDocument(DocumentMapper.toSummary(entity.getDocument()));
    response.setDueDate(entity.getDueDate());
    response.setProject(ProjectMapper.tNameAndIdProjectDTO(entity.getProject()));

    return response;
  }

  public static ToPay toEntity(RequestToPay request) {
    if (request == null) {
      return null;
    }

    ToPay response = new ToPay();

    response.setDueDate(request.getDueDate());
    response.setTypePayment(request.getTypePayment());
    response.setValue(request.getValue());

    return response;
  }
}
