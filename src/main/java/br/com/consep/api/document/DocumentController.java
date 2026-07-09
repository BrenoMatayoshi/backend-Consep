package br.com.consep.api.document;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.document.dto.SummaryDocument;
import br.com.consep.api.document.entity.Document;
import br.com.consep.api.document.service.DocumentService;
import br.com.consep.api.shared.PathConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = PathConstant.url + "/document")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

  @PostMapping("/create/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<SummaryDocument> createPath(@PathVariable Long id) {

    SummaryDocument document = documentService.createPath(id);

    URI uri = URI.create("/upload/" + document.getId());

    return ResponseEntity.created(uri).body(document);
  }

  @PutMapping(value = "/upload/{id}", consumes = "application/pdf")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<SummaryDocument> createResource(@PathVariable Long id,
      @RequestHeader("X-File-Hash") String clientHash, @RequestHeader("X-File-Name") String name,
      HttpServletRequest request) throws IOException {
    return ResponseEntity.ok().body(documentService.upload(id, clientHash, name, request.getInputStream()));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<InputStreamResource> getResource(@PathVariable Long id) throws IOException {

    Document document = documentService.findByBaseModelId(id);

    ContentDisposition contentDisposition = ContentDisposition.inline()
        .filename(document.getOriginalName())
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            contentDisposition.toString())
        .contentType(MediaType.APPLICATION_PDF)
        // .contentLength(Files.size(path))
        .body(documentService.getResource(id, document));

  }

}
