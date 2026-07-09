package br.com.consep.api.infra.pathStrategy;

import java.io.IOException;
import java.nio.file.Path;

public interface PathStrategy {
  Path resolve(String sha256) throws IOException;
}
