package br.com.consep.api.dashboard.dto;

import java.math.BigDecimal;

public record ProjectDashboardDto(
    Long qttProjects,
    Long qttProjectsCompleted,
    Long qttProjectsOngoing,
    BigDecimal valueTotal) {

}
