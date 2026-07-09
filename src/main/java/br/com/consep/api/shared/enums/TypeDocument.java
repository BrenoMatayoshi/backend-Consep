package br.com.consep.api.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeDocument {
  PAYMENT_SLIP("Nota Fiscal"),
  PAYMENT_PROOF("Comprovante");

  private final String labelPt;

  @JsonValue
  public String toJson() {
    return labelPt;
  }

  @JsonCreator
  public static TypeDocument fromJson(String value) {

    for (TypeDocument type : TypeDocument.values()) {

      if (type.name().equalsIgnoreCase(value) ||
          type.labelPt.equalsIgnoreCase(value)) {
        return type;
      }

    }

    throw new IllegalArgumentException("Tipo inválido: " + value);
  }
}
