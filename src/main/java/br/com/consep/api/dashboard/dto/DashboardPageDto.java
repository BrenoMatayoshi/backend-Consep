package br.com.consep.api.dashboard.dto;

import java.util.List;

public record DashboardPageDto(
                ProjectDashboardDto project,

                List<OrganizationDashboardDto> organizations,

                List<ToPayDashboardDto> toPay) {
}
