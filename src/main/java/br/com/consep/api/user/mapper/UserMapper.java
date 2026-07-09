package br.com.consep.api.user.mapper;

import br.com.consep.api.organization.mapper.OrganizationMapper;
import br.com.consep.api.user.dto.RequestUser;
import br.com.consep.api.user.dto.SummaryUser;
import br.com.consep.api.user.entity.User;

public class UserMapper {
  public static User toEntity(RequestUser request) {
    if (request == null) {
      return null;
    }

    User user = new User();
    user.setLogin(request.getLogin());
    user.setUsername(request.getName());
    user.setLogin(request.getLogin());
    user.setPassword(request.getPassword());
    user.setRole(request.getRole());

    return user;
  }

  public static SummaryUser toSummary(User entity) {
    if (entity == null) {
      return null;
    }
    SummaryUser response = new SummaryUser();

    response.setId(entity.getId());
    response.setName(entity.getUsername());
    response.setOrganization(OrganizationMapper.toSummary(entity.getOrganization()));
    response.setRole(entity.getRole());

    return response;
  }
}
