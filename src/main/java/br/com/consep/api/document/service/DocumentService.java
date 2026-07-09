package br.com.consep.api.document.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;

import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.document.entity.Document;

public interface DocumentService {
  SummaryDocument createPath(Long id);

  SummaryDocument upload(Long id, String clientHash, String name, InputStream inputStream);

  Document findByBaseModelId(Long id);

  InputStreamResource getResource(Long id, Document document) throws IOException;

  SummaryDocument getNameAndId(Long id);

  Document findById(Long id);
}
