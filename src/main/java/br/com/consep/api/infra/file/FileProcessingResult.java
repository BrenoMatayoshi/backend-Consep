package br.com.consep.api.infra.file;

import java.nio.file.Path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileProcessingResult {
  private String finalPath;

  private Path path;

  private Long totalBytes;
}
