package br.com.consep.api.shared.exception;

public class IntegrityException extends RuntimeException {
  public IntegrityException(String message) {
    super(message);
  }
}
