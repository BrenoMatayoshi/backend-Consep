package br.com.consep.api.publicNotice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestPublicNotice {

  // private Long id;

  @NotBlank(message = "The public notice number cannot be blank")
  private String number;

  // private LocalDate createdAt;

  // private LocalDate endAt;

  // private Organization organization;
}
