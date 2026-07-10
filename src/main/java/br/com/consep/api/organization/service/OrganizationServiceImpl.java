package br.com.consep.api.organization.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.consep.api.dashboard.dto.OrganizationDashboardDto;
import br.com.consep.api.organization.dto.DetailedOrganization;
import br.com.consep.api.organization.dto.RequestOrganization;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.organization.mapper.OrganizationMapper;
import br.com.consep.api.organization.repository.OrganizationRepository;
import br.com.consep.api.organization.specification.OrganizationSpecifications;
import br.com.consep.api.shared.dto.MetaDTO;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  @Override
  public PagedResponseDTO<?> findAll(int page, int limit, String name, String abbreviation, String sortBy,
      String sortOrder) {

    Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page, limit, sort);

    Specification<Organization> spec = Specification.<Organization>unrestricted()
        .and(OrganizationSpecifications.nameFilter(name))
        .and(OrganizationSpecifications.abbreviationFilter(abbreviation));

    Page<Organization> organizationPage = organizationRepository.findAll(spec, pageable);

    Page<SummaryOrganization> usuarioPage = organizationPage.map(OrganizationMapper::toSummary);

    MetaDTO meta = new MetaDTO(usuarioPage.getTotalElements(), usuarioPage.getTotalPages());
    return new PagedResponseDTO<>(usuarioPage.getContent(), meta);
  }

  @Override
  public SummaryOrganization createOrganization(RequestOrganization request) {
    Organization organization = OrganizationMapper.toEntity(request);

    if (organization == null) {
      return null;
    }

    Organization saved = organizationRepository.save(organization);

    saved = organizationRepository.findById(saved.getId())
        .orElseThrow();

    return OrganizationMapper.toSummary(saved);
  }

  @Override
  public DetailedOrganization getOrganization(Long id) {
    return OrganizationMapper.toDetailedOrganization(findById(id));
  }

  @Override
  public Organization findById(Long id) {
    return organizationRepository.findById(id)
        .orElseThrow(() -> new ElementNotFoundException("This organization does not exists"));
  }

  @Override
  public List<OrganizationDashboardDto> findLimited(int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return organizationRepository.findOrganizationDashboard(pageable);
  }
}
