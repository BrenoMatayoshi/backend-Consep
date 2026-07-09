package br.com.consep.api.document.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.consep.api.document.dto.SummaryDocumentProjection;
import br.com.consep.api.document.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
  Optional<Document> findByEntityId(Long id);

  Optional<SummaryDocumentProjection> findAllByEntityId(Long id);
}
