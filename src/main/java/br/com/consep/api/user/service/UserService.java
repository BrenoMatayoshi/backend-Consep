package br.com.consep.api.user.service;

import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.user.dto.ChangeRoleUserDTO;
import br.com.consep.api.user.dto.RequestUser;
import br.com.consep.api.user.dto.SummaryUser;
import br.com.consep.api.user.entity.User;

public interface UserService {
  PagedResponseDTO<?> findAll(
      int page,
      int limit,
      String name,
      String sortBy,
      String sortOrder);

  SummaryUser createUser(RequestUser request, Long organizationId);

  User findUserById(Long id);

  SummaryUser findUserProfile(Long id);

  SummaryUser updateUserRole(Long id, ChangeRoleUserDTO newRole);
}
