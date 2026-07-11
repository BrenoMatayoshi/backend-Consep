package br.com.consep.api.organization.service;

import java.util.List;

import br.com.consep.api.dashboard.dto.OrganizationDashboardDto;
import br.com.consep.api.organization.dto.DetailedOrganization;
import br.com.consep.api.organization.dto.RequestOrganization;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.shared.dto.PagedResponseDTO;

public interface OrganizationService {
  PagedResponseDTO<?> findAll(
      int page,
      int limit,
      String name,
      String abbreviation,
      String sortBy,
      String sortOrder);

  SummaryOrganization createOrganization(RequestOrganization request);

  Organization findById(Long id);

  DetailedOrganization getOrganization(Long id);

  List<OrganizationDashboardDto> findLimited(int limit);

  void deleteOrganization(Long id);

  SummaryOrganization updateOrganization(Long id, RequestOrganization request);
}
