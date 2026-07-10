package br.com.consep.api.organization.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.consep.api.dashboard.dto.OrganizationDashboardDto;
import br.com.consep.api.organization.dto.DetailedOrganization;
import br.com.consep.api.organization.dto.RequestOrganization;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.organization.mapper.OrganizationMapper;
import br.com.consep.api.organization.repository.OrganizationRepository;
import br.com.consep.api.organization.specification.OrganizationSpecifications;
import br.com.consep.api.project.repository.ProjectRepository;
import br.com.consep.api.publicNotice.repository.PublicNoticeRepository;
import br.com.consep.api.shared.dto.MetaDTO;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import br.com.consep.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  private final ProjectRepository projectRepository;

  private final PublicNoticeRepository publicNoticeRepository;

  private final UserRepository userRepository;

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

    Long savedId = Objects.requireNonNull(saved.getId());

    saved = organizationRepository.findById(savedId)
        .orElseThrow();

    return OrganizationMapper.toSummary(saved);
  }

  @Override
  public DetailedOrganization getOrganization(Long id) {
    return OrganizationMapper.toDetailedOrganization(findById(id));
  }

  @Override
  public Organization findById(Long id) {
    Long organizationId = Objects.requireNonNull(id);

    return organizationRepository.findById(organizationId)
        .orElseThrow(() -> new ElementNotFoundException("This organization does not exists"));
  }

  @Override
  public List<OrganizationDashboardDto> findLimited(int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return organizationRepository.findOrganizationDashboard(pageable);
  }

  @Override
  @Transactional
  public void deleteOrganization(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("This organization does not exists");
    }

    projectRepository.deleteByOrganizationId(id);
    publicNoticeRepository.deleteByOrganizationId(id);
    userRepository.deleteByOrganizationId(id);
    organizationRepository.deleteById(id);
  }
}
