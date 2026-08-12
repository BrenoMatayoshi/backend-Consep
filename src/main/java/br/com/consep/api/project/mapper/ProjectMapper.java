package br.com.consep.api.project.mapper;

import br.com.consep.api.document.mapper.DocumentMapper;
import br.com.consep.api.organization.mapper.OrganizationMapper;
import br.com.consep.api.project.dto.MinimalProject;
import br.com.consep.api.project.dto.NameAndIdProjectDTO;
import br.com.consep.api.project.dto.RequestProject;
import br.com.consep.api.project.dto.SummaryProject;
import br.com.consep.api.project.dto.CompleteProject;
import br.com.consep.api.project.entity.Project;
import br.com.consep.api.publicNotice.mapper.PublicNoticeMapper;
import br.com.consep.api.toPay.mapper.ToPayMapper;

public class ProjectMapper {
  public static Project toEntity(RequestProject request) {
    if (request == null) {
      return null;
    }

    Project response = new Project();

    response.setName(request.getName());
    response.setValue(request.getValue());

    return response;
  }

  public static CompleteProject toCompleteProject(Project entity) {
    if (entity == null) {
      return null;
    }

    CompleteProject response = new CompleteProject();

    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setOrganization(OrganizationMapper.toSummary(entity.getOrganization()));
    response.setNotice(PublicNoticeMapper.toSummary(entity.getNotice()));
    if (entity.getToPay() != null) {
      response.setToPay(entity.getToPay().stream().map(ToPayMapper::toSummary).toList());
    }
    response.setValue(entity.getValue());
    response.setProjectStatus(entity.getProjectStatus());
    response.setDocument(DocumentMapper.toSummary(entity.getDocument()));

    return response;
  }

  public static SummaryProject toSummary(Project entity) {
    if (entity == null) {
      return null;
    }

    SummaryProject response = new SummaryProject();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setStatus(entity.getProjectStatus());
    response.setValue(entity.getValue());
    response.setNoticeNumber(entity.getNotice().getNumber());
    response.setDocument(DocumentMapper.toSummary(entity.getDocument()));
    response.setOrganization(OrganizationMapper.toSummary(entity.getOrganization()));

    return response;
  }

  public static MinimalProject toMinimal(Project entity) {
    if (entity == null) {
      return null;
    }

    MinimalProject response = new MinimalProject();
    response.setId(entity.getId());
    System.out.println("TESTE: " + entity.getId());
    response.setName(entity.getName());
    response.setValue(entity.getValue());
    response.setStatus(entity.getProjectStatus());
    response.setOrganization(OrganizationMapper.toSummary(entity.getOrganization()));

    return response;
  }

  public static NameAndIdProjectDTO tNameAndIdProjectDTO(Project entity) {
    if (entity == null) {
      return null;
    }

    NameAndIdProjectDTO response = new NameAndIdProjectDTO();

    response.setId(entity.getId());
    response.setName(entity.getName());
    return response;
  }
}
