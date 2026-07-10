package br.com.consep.api.document.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import br.com.consep.api.infra.pathStrategy.PathStrategy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentFileCleanupService {

  private final PathStrategy pathStrategy;

  public void deleteFile(String sha256) {
    if (sha256 == null || sha256.isBlank()) {
      return;
    }

    try {
      Path path = pathStrategy.resolveForDeletion(sha256);
      Files.deleteIfExists(path);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to delete document file", e);
    }
  }
}