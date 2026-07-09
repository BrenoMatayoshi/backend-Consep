package br.com.consep.api.payment.service;

import org.springframework.stereotype.Service;

import br.com.consep.api.payment.dto.RequestPayment;
import br.com.consep.api.payment.dto.SummaryPayment;
import br.com.consep.api.payment.entity.Payment;
import br.com.consep.api.payment.mapper.PaymentMapper;
import br.com.consep.api.toPay.service.ToPayService;
import lombok.RequiredArgsConstructor;
import br.com.consep.api.payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final ToPayService toPayService;

  private final PaymentRepository paymentRepository;

  @Override
  public SummaryPayment createPayment(RequestPayment request, Long toPayId) {
    Payment payment = PaymentMapper.toEntity(request);
    payment.setToPay(toPayService.findById(toPayId));
    payment.setValue(payment.getToPay().getValue());

    return PaymentMapper.toSummary(paymentRepository.save(payment));
  }
}
