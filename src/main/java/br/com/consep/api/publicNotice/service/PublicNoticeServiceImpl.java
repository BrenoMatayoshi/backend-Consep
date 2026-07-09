package br.com.consep.api.publicNotice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.consep.api.organization.entity.Organization;
import br.com.consep.api.organization.service.OrganizationService;
import br.com.consep.api.publicNotice.dto.RequestPublicNotice;
import br.com.consep.api.publicNotice.dto.SummaryPublicNotice;
import br.com.consep.api.publicNotice.entity.PublicNotice;
import br.com.consep.api.publicNotice.mapper.PublicNoticeMapper;
import br.com.consep.api.publicNotice.repository.PublicNoticeRepository;
import br.com.consep.api.shared.enums.Role;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import br.com.consep.api.user.entity.User;
import br.com.consep.api.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PublicNoticeServiceImpl implements PublicNoticeService {

  private final PublicNoticeRepository publicNoticeRepository;

  private final UserService userService;

  private final OrganizationService organizationService;

  @Override
  public PublicNotice findById(Long id) {
    return publicNoticeRepository.findById(id)
        .orElseThrow(() -> new ElementNotFoundException("Public notice not found"));
  }

  @Override
  public List<SummaryPublicNotice> findAll() {
    User user = userService.findUserById(Long.decode("1"));
    if (user.getRole().equals(Role.ADMIN)) {
      return publicNoticeRepository.findAll().stream().map(PublicNoticeMapper::toSummary).toList();
    }
    if (user.getOrganization() == null) {
      throw new ElementNotFoundException("This user does not belong to any organization");
    }
    return publicNoticeRepository.findPublicNoticeByOrganizationId(user.getOrganization().getId()).stream()
        .map(PublicNoticeMapper::toSummary).toList();
  }

  @Override
  public SummaryPublicNotice createPublicNotice(RequestPublicNotice request, Long organizationId) {
    PublicNotice publicNotice = PublicNoticeMapper.toEntity(request);

    Organization organization = organizationService.findById(organizationId);
    publicNotice.setOrganization(organization);

    return PublicNoticeMapper.toSummary(publicNoticeRepository.save(publicNotice));
  }
}
