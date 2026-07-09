package br.com.consep.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.consep.api.shared.exception.ElementNotFoundException;
import br.com.consep.api.shared.exception.ErrorResponse;
import br.com.consep.api.shared.exception.FileConflictException;
import br.com.consep.api.shared.exception.IntegrityException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @Nullable
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach(e -> errors.add(e.getDefaultMessage()));
    return ResponseEntity.badRequest().body(new ErrorResponse(errors));
  }

  @ExceptionHandler(exception = ElementNotFoundException.class)
  protected ResponseEntity<Object> handleElementNotFoundException(ElementNotFoundException ex) {
    return new ResponseEntity<>(new ErrorResponse(Arrays.asList(ex.getMessage())), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(exception = IntegrityException.class)
  protected ResponseEntity<Object> handleIntegrityException(IntegrityException ex) {
    return new ResponseEntity<>(new ErrorResponse(Arrays.asList(ex.getMessage())), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(exception = FileConflictException.class)
  protected ResponseEntity<Object> handleFileConflictException(FileConflictException ex) {
    return new ResponseEntity<>(new ErrorResponse(Arrays.asList(ex.getMessage())), HttpStatus.CONFLICT);
  }
}
