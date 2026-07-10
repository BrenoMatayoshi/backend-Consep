package br.com.consep.api.project.service;

import br.com.consep.api.project.dto.RequestProject;
import br.com.consep.api.project.dto.SummaryProject;
import br.com.consep.api.project.dto.UpdateStatusProject;

import java.math.BigDecimal;

import br.com.consep.api.dashboard.dto.ProjectDashboardDto;
import br.com.consep.api.project.dto.CompleteProject;
import br.com.consep.api.project.entity.Project;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.enums.ProjectStatus;
import br.com.consep.api.user.entity.User;

public interface ProjectService {
  PagedResponseDTO<?> findAll(
      int page,
      int limit,
      String name,
      BigDecimal value,
      ProjectStatus projectStatus,
      String noticeNumber,
      String sortBy,
      String sortOrder,
      User user);

  CompleteProject createProject(RequestProject request, Long organizationId, Long publicNoticeId);

  CompleteProject getProject(Long id);

  Project findById(Long id);

  ProjectDashboardDto getProjectDashboard();

  SummaryProject updateProjectStatus(Long id, UpdateStatusProject newStatus);

  int countProjectByOrganizationId(Long id);

  void deleteProject(Long id);
}
