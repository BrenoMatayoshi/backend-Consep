package br.com.consep.api.organization.mapper;

import br.com.consep.api.organization.dto.DetailedOrganization;
import br.com.consep.api.organization.dto.RequestOrganization;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.project.mapper.ProjectMapper;
import br.com.consep.api.publicNotice.mapper.PublicNoticeMapper;

public class OrganizationMapper {

  public static Organization toEntity(RequestOrganization request) {
    if (request == null) {
      return null;
    }

    Organization entity = new Organization();

    entity.setName(request.getName());
    entity.setAbbreviation(request.getAbbreviation());

    return entity;
  }

  public static SummaryOrganization toSummary(Organization entity) {
    if (entity == null) {
      return null;
    }

    SummaryOrganization response = new SummaryOrganization();

    response.setId(entity.getId());
    response.setAbbreviation(entity.getAbbreviation());
    response.setName(entity.getName());

    response.setNoticesCount(entity.getNoticesCount());
    response.setProjectsCount(entity.getProjectsCount());
    response.setUsersCount(entity.getUsersCount());

    return response;
  }

  public static DetailedOrganization toDetailedOrganization(Organization request) {
    if (request == null) {
      return null;
    }

    DetailedOrganization response = new DetailedOrganization();
    response.setAbbreviation(request.getAbbreviation());
    response.setId(request.getId());
    response.setName(request.getName());
    response.setNotices(request.getNotices().stream().map(PublicNoticeMapper::toSummary).toList());
    response.setProjects(request.getProjects().stream().map(ProjectMapper::toSummary).toList());

    return response;
  }
}
