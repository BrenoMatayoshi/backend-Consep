package br.com.consep.api.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.consep.api.shared.enums.ToPayStatus;

public record ToPayDashboardDto(
    String originalName,
    Long id,
    BigDecimal value,
    LocalDate dueDate,
    ToPayStatus status) {
}
