package br.com.consep.api.user.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.organization.service.OrganizationService;
import br.com.consep.api.shared.dto.MetaDTO;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import br.com.consep.api.user.dto.ChangeRoleUserDTO;
import br.com.consep.api.user.dto.RequestUser;
import br.com.consep.api.user.dto.SummaryUser;
import br.com.consep.api.user.entity.User;
import br.com.consep.api.user.mapper.UserMapper;
import br.com.consep.api.user.repository.UserRepository;
import br.com.consep.api.user.specification.UserSpecifications;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final OrganizationService organizationService;

  private final PasswordEncoder passwordEncoder;

  @Override
  public PagedResponseDTO<?> findAll(
      int page,
      int limit,
      String name,
      String sortBy,
      String sortOrder) {

    Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page, limit, sort);

    Specification<User> spec = Specification
        .<User>unrestricted().and(UserSpecifications.nameFilter(name));

    Page<User> userPage = userRepository.findAll(spec, pageable);

    Page<SummaryUser> usuarioPage = userPage.map(UserMapper::toSummary);

    MetaDTO meta = new MetaDTO(usuarioPage.getTotalElements(), usuarioPage.getTotalPages());
    return new PagedResponseDTO<>(usuarioPage.getContent(), meta);
  }

  @Override
  public SummaryUser createUser(RequestUser request, Long organizationId) {
    Organization organization = organizationService.findById(organizationId);

    User user = UserMapper.toEntity(request);

    user.setOrganization(organization);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return UserMapper.toSummary(userRepository.save(user));
  }

  @Override
  public SummaryUser findUserProfile(Long id) {
    User user = findUserById(id);

    return UserMapper.toSummary(user);
  }

  @Override
  public User findUserById(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("User not found");
    }

    Optional<User> user = userRepository.findById(id);

    return user.orElseThrow(() -> new ElementNotFoundException("User not found"));
  }

  @Override
  public SummaryUser updateUserRole(Long id, ChangeRoleUserDTO newRole) {
    if (id == null) {
      throw new ElementNotFoundException("Boleto não encontrado");
    }
    User user = findUserById(id);
    user.setRole(newRole.getRole());
    return UserMapper.toSummary(userRepository.save(user));
  }

  @Override
  public User findUserByLogin(String login) {
    return userRepository.findByLogin(login).get();
  }

}
