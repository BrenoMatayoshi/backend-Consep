package br.com.consep.api.infra.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.security.NoSuchAlgorithmException;

import org.springframework.core.io.InputStreamResource;

import br.com.consep.api.document.entity.Document;
import br.com.consep.api.infra.file.FileProcessingResult;

public interface FileProcessingService {
  FileProcessingResult storeAndHash(InputStream inputStream, String clientHash, Document document)
      throws NoSuchAlgorithmException, IOException, FileAlreadyExistsException;

  InputStreamResource getResource(String hash) throws IOException;
}
