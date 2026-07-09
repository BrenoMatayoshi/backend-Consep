package br.com.consep.api.payment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.shared.PathConstant;
import br.com.consep.api.payment.dto.RequestPayment;
import br.com.consep.api.payment.dto.SummaryPayment;
import br.com.consep.api.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathConstant.url + "/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/toPay/{toPayId}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<SummaryPayment> createPayment(@Valid @RequestBody RequestPayment requestPayment,
      @PathVariable Long toPayId) {

    return ResponseEntity.ok(paymentService.createPayment(requestPayment, toPayId));
  }
}
