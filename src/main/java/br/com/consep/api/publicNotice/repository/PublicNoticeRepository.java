package br.com.consep.api.publicNotice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consep.api.publicNotice.entity.PublicNotice;

public interface PublicNoticeRepository extends JpaRepository<PublicNotice, Long> {
  List<PublicNotice> findPublicNoticeByOrganizationId(Long id);

  void deleteByOrganizationId(Long organizationId);
}
