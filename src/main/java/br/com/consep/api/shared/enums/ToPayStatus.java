package br.com.consep.api.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ToPayStatus {
  PENDING("Aguardando"),
  SCHEDULED("Agendado"),
  PAID("Pago"),
  OVERDUE("Vencido");

  private final String labelPt;

  @JsonValue
  public String toJson() {
    return labelPt;
  }

  @JsonCreator
  public static ToPayStatus fromJson(String value) {

    for (ToPayStatus status : ToPayStatus.values()) {

      if (status.name().equalsIgnoreCase(value) ||
          status.labelPt.equalsIgnoreCase(value)) {
        return status;
      }

    }

    throw new IllegalArgumentException("Status inválido: " + value);
  }
}
