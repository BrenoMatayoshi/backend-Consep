package br.com.consep.api.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatus {
  COMPLETED("Completo"),
  FINISHED("Finalizado"),
  ONGOING("Em andamento"),
  CANCELLED("Cancelado");

  private final String labelPt;

  @JsonValue
  public String toJson() {
    return labelPt;
  }

  @JsonCreator
  public static ProjectStatus fromJson(String value) {

    for (ProjectStatus status : ProjectStatus.values()) {

      if (status.name().equalsIgnoreCase(value) ||
          status.labelPt.equalsIgnoreCase(value)) {
        return status;
      }

    }

    throw new IllegalArgumentException("Status inválido: " + value);
  }
}