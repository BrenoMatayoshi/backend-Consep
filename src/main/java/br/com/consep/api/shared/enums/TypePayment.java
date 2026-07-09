package br.com.consep.api.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypePayment {
  INVOICE("Boleto"),
  PIX("Pix");

  private final String labelPt;

  @JsonValue
  public String toJson() {
    return labelPt;
  }

  @JsonCreator
  public static TypePayment fromJson(String value) {

    for (TypePayment type : TypePayment.values()) {

      if (type.name().equalsIgnoreCase(value) ||
          type.labelPt.equalsIgnoreCase(value)) {
        return type;
      }

    }

    throw new IllegalArgumentException("Tipo inválido: " + value);
  }
}
