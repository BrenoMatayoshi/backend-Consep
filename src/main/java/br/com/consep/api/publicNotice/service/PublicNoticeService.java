package br.com.consep.api.publicNotice.service;

import java.util.List;

import br.com.consep.api.publicNotice.dto.RequestPublicNotice;
import br.com.consep.api.publicNotice.dto.SummaryPublicNotice;
import br.com.consep.api.publicNotice.entity.PublicNotice;

public interface PublicNoticeService {
  PublicNotice findById(Long id);

  List<SummaryPublicNotice> findAll();

  SummaryPublicNotice createPublicNotice(RequestPublicNotice request, Long organizationId);
}
