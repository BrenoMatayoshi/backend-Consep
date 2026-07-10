package br.com.consep.api.project.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.consep.api.dashboard.dto.ProjectDashboardDto;
import br.com.consep.api.organization.service.OrganizationService;
import br.com.consep.api.project.dto.MinimalProject;
import br.com.consep.api.project.dto.RequestProject;
import br.com.consep.api.project.dto.SummaryProject;
import br.com.consep.api.project.dto.UpdateStatusProject;
import br.com.consep.api.project.dto.CompleteProject;
import br.com.consep.api.project.entity.Project;
import br.com.consep.api.project.mapper.ProjectMapper;
import br.com.consep.api.project.repository.ProjectRepository;
import br.com.consep.api.project.specification.ProjectSpecifications;
import br.com.consep.api.publicNotice.service.PublicNoticeService;
import br.com.consep.api.shared.dto.MetaDTO;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.enums.ProjectStatus;
import br.com.consep.api.shared.enums.Role;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import br.com.consep.api.toPay.repository.ToPayRepository;
import br.com.consep.api.user.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  private final OrganizationService organizationService;

  private final PublicNoticeService publicNoticeService;

  private final ToPayRepository toPayRepository;

  @Override
  public PagedResponseDTO<?> findAll(
      int page,
      int limit,
      String name,
      BigDecimal value,
      ProjectStatus projectStatus,
      String noticeNumber,
      String sortBy,
      String sortOrder,
      User user) {
    Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page, limit, sort);

    if (user.getRole().equals(Role.ADMIN)) {
      Specification<Project> spec = Specification.allOf(
          ProjectSpecifications.valueFilter(value),
          ProjectSpecifications.nameFilter(name),
          ProjectSpecifications.noticeNumberFilter(noticeNumber),
          ProjectSpecifications.projectStatusFilter(projectStatus));

      Page<Project> projectPage = projectRepository.findAll(spec, pageable);

      Page<SummaryProject> usuarioPage = projectPage.map(ProjectMapper::toSummary);

      MetaDTO meta = new MetaDTO(usuarioPage.getTotalElements(), usuarioPage.getTotalPages());
      return new PagedResponseDTO<>(usuarioPage.getContent(), meta);
    }
    if (user.getOrganization() == null) {
      throw new ElementNotFoundException("Esse usuário não pertence a nenhuma organização");
    }
    Specification<Project> spec = Specification.allOf(
        ProjectSpecifications.organizationId(user.getOrganization().getId()),
        ProjectSpecifications.valueFilter(value),
        ProjectSpecifications.nameFilter(name),
        ProjectSpecifications.noticeNumberFilter(noticeNumber),
        ProjectSpecifications.projectStatusFilter(projectStatus));

    Page<Project> projectPage = projectRepository.findAll(spec,
        pageable);

    Page<MinimalProject> usuarioPage = projectPage.map(ProjectMapper::toMinimal);

    MetaDTO meta = new MetaDTO(usuarioPage.getTotalElements(), usuarioPage.getTotalPages());
    return new PagedResponseDTO<>(usuarioPage.getContent(), meta);
  }

  @Override
  public CompleteProject createProject(RequestProject request, Long organizationId, Long publicNoticeId) {

    Project project = ProjectMapper.toEntity(request);
    project.setOrganization(organizationService.findById(organizationId));
    project.setNotice(publicNoticeService.findById(publicNoticeId));
    project.setProjectStatus(ProjectStatus.ONGOING);

    return ProjectMapper.toCompleteProject(projectRepository.save(project));
  }

  @Override
  public CompleteProject getProject(Long id) {
    CompleteProject summary = ProjectMapper.toCompleteProject(findById(id));
    summary.setValueUsed(toPayRepository.getSumByProjectId(id));
    return summary;
  }

  @Override
  public Project findById(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Projeto não encontrado");
    }
    return projectRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Projeto não encontrado"));
  }

  @Override
  public ProjectDashboardDto getProjectDashboard() {
    return projectRepository.findProjectDashboard();
  }

  @Override
  public SummaryProject updateProjectStatus(Long id, UpdateStatusProject newStatus) {
    if (id == null) {
      throw new ElementNotFoundException("Boleto não encontrado");
    }
    Project project = findById(id);
    project.setProjectStatus(newStatus.getStatus());
    return ProjectMapper.toSummary(projectRepository.save(project));
  }

  @Override
  public int countProjectByOrganizationId(Long id) {
    return projectRepository.countProjectByOrganizationId(id);
  }

  @Override
  @Transactional
  public void deleteProject(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Projeto não encontrado");
    }

    Project project = Objects.requireNonNull(findById(id));
    projectRepository.delete(project);
    projectRepository.flush();
  }
}
