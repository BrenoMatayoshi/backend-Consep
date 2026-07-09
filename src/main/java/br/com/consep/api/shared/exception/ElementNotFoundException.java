package br.com.consep.api.shared.exception;

public class ElementNotFoundException extends RuntimeException {
  public ElementNotFoundException(String message) {
    super(message);
  }
}
