package br.com.consep.api.shared.exception;

public class FileConflictException extends RuntimeException {
    public FileConflictException(String message) {
        super(message);
    }
}
