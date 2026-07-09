package br.com.consep.api.payment.service;

import br.com.consep.api.payment.dto.RequestPayment;
import br.com.consep.api.payment.dto.SummaryPayment;

public interface PaymentService {

  SummaryPayment createPayment(RequestPayment request, Long toPayId);
}
