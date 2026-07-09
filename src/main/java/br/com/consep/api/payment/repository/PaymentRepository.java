package br.com.consep.api.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consep.api.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
