package br.com.consep.api.document.listener;

import br.com.consep.api.document.entity.Document;
import br.com.consep.api.document.service.DocumentFileCleanupService;
import br.com.consep.api.infra.spring.SpringContext;
import jakarta.persistence.PreRemove;

public class DocumentEntityListener {

  @PreRemove
  public void preRemove(Document document) {
    DocumentFileCleanupService cleanupService = SpringContext.getBean(DocumentFileCleanupService.class);

    if (cleanupService != null) {
      cleanupService.deleteFile(document.getSha256());
    }
  }
}