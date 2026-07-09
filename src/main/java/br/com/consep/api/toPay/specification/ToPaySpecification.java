package br.com.consep.api.toPay.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.consep.api.shared.enums.ToPayStatus;
import br.com.consep.api.toPay.entity.ToPay;

public class ToPaySpecification {
  public static Specification<ToPay> valueFilter(BigDecimal value) {
    return (root, query, cb) -> value == null
        ? cb.conjunction()
        : cb.equal(root.get("value"), value);
  }

  public static Specification<ToPay> toPayStatusFilter(ToPayStatus status) {
    return (root, query, cb) -> status == null
        ? cb.conjunction()
        : cb.equal(root.get("status"), status);
  }

  public static Specification<ToPay> dueDateFilter(LocalDate date) {
    return (root, query, cb) -> {
      if (date == null) {
        return cb.conjunction();
      }

      return cb.equal(root.get("dueDate"), date);
    };
  }
}