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

  // O Spring vai injetar o caminho configurado. Se não houver, ele usa
  // "/app/assets" como padrão.
  public HashBasedStrategy(@Value("${app.upload.dir:/app/assets}") String uploadDir) {
    this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
  }

  @Override
  public Path resolve(String sha256) throws IOException {
    String dir1 = sha256.substring(0, 2);
    String dir2 = sha256.substring(2, 4);

    Path directory = root.resolve(dir1).resolve(dir2);

    Files.createDirectories(directory);

    return directory.resolve(sha256 + ".pdf");
  }
}
