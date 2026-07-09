package br.com.consep.api.publicNotice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.consep.api.publicNotice.entity.PublicNotice;

@Repository
public interface PublicNoticeRepository extends JpaRepository<PublicNotice, Long> {
  List<PublicNotice> findPublicNoticeByOrganizationId(Long id);
}
