package br.com.consep.api.infra.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.consep.api.base.service.BaseModelService;
import br.com.consep.api.document.entity.Document;
import br.com.consep.api.document.repository.DocumentRepository;
import br.com.consep.api.infra.file.FileProcessingResult;
import br.com.consep.api.infra.pathStrategy.PathStrategy;
import br.com.consep.api.shared.exception.FileConflictException;
import br.com.consep.api.shared.exception.IntegrityException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileProcessingServiceImpl implements FileProcessingService {

  private final PathStrategy pathStrategy;

  private final DocumentRepository documentRepository;

  private final BaseModelService baseModelService;

  @Override
  @Transactional(noRollbackFor = FileConflictException.class)
  public FileProcessingResult storeAndHash(InputStream inputStream, String clientHash, Document document)
      throws NoSuchAlgorithmException, IOException, FileAlreadyExistsException {

    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    Path tempFile = Files.createTempFile("upload-", ".tmp");

    long totalBytes = 0;

    try (DigestInputStream dis = new DigestInputStream(inputStream, digest);
        OutputStream os = Files.newOutputStream(tempFile)) {
      byte[] buffer = new byte[8192];
      int read;

      while ((read = dis.read(buffer)) != -1) {
        os.write(buffer, 0, read);
        totalBytes += read;
      }
    }

    String hash = bytesToHex(digest.digest());

    Path finalPath = pathStrategy.resolve(hash);

    if (!clientHash.equalsIgnoreCase(hash)) {
      Files.deleteIfExists(tempFile);
      throw new IntegrityException("Arquivo corrompido");
    }

    try {
      Files.move(tempFile, finalPath);
    } catch (FileAlreadyExistsException e) {
      Files.deleteIfExists(tempFile);
      documentRepository.delete(document);
      baseModelService.deleteById(document.getEntity().getId());
      documentRepository.flush();
      throw new FileConflictException("Arquivo já existe");
    }

    return new FileProcessingResult(hash, finalPath, totalBytes);
  }

  @Override
  public InputStreamResource getResource(String hash) throws IOException {

    Path path = pathStrategy.resolve(hash);

    return new InputStreamResource(Files.newInputStream(path));
  }

  private String bytesToHex(byte[] bytes) {
    return HexFormat.of().formatHex(bytes);
  }
}
