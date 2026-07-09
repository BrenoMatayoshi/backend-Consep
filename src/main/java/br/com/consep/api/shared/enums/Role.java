package br.com.consep.api.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  ADMIN("Admin"),
  USER("User");

  private final String labelPt;

  @JsonValue
  public String toJson() {
    return labelPt;
  }

  @JsonCreator
  public static Role fromJson(String value) {

    for (Role role : Role.values()) {

      if (role.name().equalsIgnoreCase(value) ||
          role.labelPt.equalsIgnoreCase(value)) {
        return role;
      }

    }

    throw new IllegalArgumentException("Cargo inválido: " + value);
  }
}
