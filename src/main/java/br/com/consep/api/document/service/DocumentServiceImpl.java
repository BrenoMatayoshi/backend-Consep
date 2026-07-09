package br.com.consep.api.document.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import br.com.consep.api.base.BaseModel;
import br.com.consep.api.base.service.BaseModelService;
import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.document.entity.Document;
import br.com.consep.api.document.mapper.DocumentMapper;
import br.com.consep.api.document.repository.DocumentRepository;
import br.com.consep.api.infra.file.FileProcessingResult;
import br.com.consep.api.infra.file.service.FileProcessingService;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

  private final DocumentRepository documentRepository;

  private final BaseModelService baseModelService;

  private final FileProcessingService fileProcessingService;

  @Override
  public SummaryDocument createPath(Long id) {
    Document document = new Document();

    BaseModel baseModel = baseModelService.findById(id);

    document.setEntity(baseModel);

    return DocumentMapper.toSummary(documentRepository.save(document));
  }

  @Override
  public SummaryDocument upload(Long id, String clientHash, String name, InputStream inputStream) {

    Document document = findById(id);

    try {
      FileProcessingResult result = fileProcessingService.storeAndHash(inputStream, clientHash, document);
      document.setSha256(result.getFinalPath());
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }

    document.setOriginalName(name);

    return DocumentMapper.toSummary(documentRepository.save(document));
  }

  @Override
  public InputStreamResource getResource(Long id, Document document) throws IOException {
    return fileProcessingService.getResource(document.getSha256());
  }

  @Override
  public Document findByBaseModelId(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Documento não encontrado");
    }
    return documentRepository.findByEntityId(id)
        .orElseThrow(() -> new ElementNotFoundException("Documento não encontrado"));
  }

  @Override
  public Document findById(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Documento não encontrado");
    }
    return documentRepository.findById(id)
        .orElseThrow(() -> new ElementNotFoundException("Documento não encontrado"));
  }

  @Override
  public SummaryDocument getNameAndId(Long id) {
    SummaryDocument summary = DocumentMapper.toSummary(documentRepository.findAllByEntityId(id)
        .orElseThrow(() -> new ElementNotFoundException("Documento não encontrado.")));

    return summary;
  }
}
