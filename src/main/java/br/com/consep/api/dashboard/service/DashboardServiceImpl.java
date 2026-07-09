package br.com.consep.api.dashboard.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.consep.api.dashboard.dto.DashboardPageDto;
import br.com.consep.api.dashboard.dto.OrganizationDashboardDto;
import br.com.consep.api.dashboard.dto.ProjectDashboardDto;
import br.com.consep.api.dashboard.dto.ToPayDashboardDto;
import br.com.consep.api.dashboard.mapper.DashboardMapper;
import br.com.consep.api.organization.service.OrganizationService;
import br.com.consep.api.project.service.ProjectService;
import br.com.consep.api.shared.enums.ToPayStatus;
import br.com.consep.api.toPay.service.ToPayService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

  private final OrganizationService organizationService;
  private final ToPayService toPayService;
  private final ProjectService projectService;

  private final int limit = 3;

  @Override
  public DashboardPageDto getDashboardData() {
    List<OrganizationDashboardDto> organizations = organizationService.findLimited(limit);
    List<ToPayDashboardDto> toPays = toPayService.findByStatus(ToPayStatus.PENDING, limit).stream()
        .map(DashboardMapper::toPayDashboardDto).toList();
    ProjectDashboardDto project = projectService.getProjectDashboard();
    return new DashboardPageDto(project, organizations, toPays);
  }

}
