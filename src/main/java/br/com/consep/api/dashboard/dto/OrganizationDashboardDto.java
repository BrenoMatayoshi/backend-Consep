package br.com.consep.api.dashboard.dto;

public record OrganizationDashboardDto(
        Long id,
        String name,
        Long usersCount,
        Long projectCount,
        Long publicNoticeCount) {
}
