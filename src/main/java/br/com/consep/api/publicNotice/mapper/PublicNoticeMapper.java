package br.com.consep.api.publicNotice.mapper;

import br.com.consep.api.publicNotice.dto.RequestPublicNotice;
import br.com.consep.api.publicNotice.dto.SummaryPublicNotice;
import br.com.consep.api.publicNotice.entity.PublicNotice;

public class PublicNoticeMapper {

  public static PublicNotice toEntity(RequestPublicNotice request) {
    if (request == null) {
      return null;
    }

    PublicNotice response = new PublicNotice();

    response.setNumber(request.getNumber());

    return response;
  }

  public static SummaryPublicNotice toSummary(PublicNotice entity) {
    if (entity == null) {
      return null;
    }

    SummaryPublicNotice response = new SummaryPublicNotice();

    response.setId(entity.getId());
    response.setNumber(entity.getNumber());
    response.setOrganizationName(entity.getOrganization().getName());

    return response;
  }
}
