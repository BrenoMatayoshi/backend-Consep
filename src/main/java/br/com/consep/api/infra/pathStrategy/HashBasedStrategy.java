package br.com.consep.api.infra.pathStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HashBasedStrategy implements PathStrategy {

  private final Path root;

  public HashBasedStrategy(@Value("${app.upload.dir:/app/assets}") String uploadDir) {
    this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
  }

  @Override
  public Path resolve(String sha256) throws IOException {
    Path filePath = resolveForDeletion(sha256);

    Files.createDirectories(filePath.getParent());

    return filePath;
  }

  @Override
  public Path resolveForDeletion(String sha256) {
    String dir1 = sha256.substring(0, 2);
    String dir2 = sha256.substring(2, 4);

    return root.resolve(dir1).resolve(dir2).resolve(sha256 + ".pdf");
  }
}
