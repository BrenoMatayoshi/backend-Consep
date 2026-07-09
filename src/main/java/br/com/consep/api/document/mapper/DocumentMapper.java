package br.com.consep.api.document.mapper;

import br.com.consep.api.document.dto.RequestDocument;
import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.document.dto.SummaryDocumentProjection;
import br.com.consep.api.document.entity.Document;

public class DocumentMapper {

  public static Document toEntity(RequestDocument request) {
    if (request == null) {
      return null;
    }

    Document response = new Document();

    response.setSha256(request.getSha256());

    return response;
  }

  public static SummaryDocument toSummary(Document entity) {
    if (entity == null) {
      return null;
    }

    SummaryDocument response = new SummaryDocument();

    response.setId(entity.getId());
    response.setOriginalName(entity.getOriginalName());

    return response;
  }

  public static SummaryDocument toSummary(SummaryDocumentProjection projection) {
    if (projection == null) {
      return null;
    }

    SummaryDocument response = new SummaryDocument();

    response.setId(projection.getId());
    response.setOriginalName(projection.getOriginalName());

    return response;
  }
}
