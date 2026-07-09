package br.com.consep.api.shared.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@Getter
public class ErrorResponse {

  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timeStamp;
  private List<String> errors;

  public ErrorResponse(List<String> errors) {
    this.timeStamp = LocalDateTime.now();
    this.errors = errors;
  }
}
