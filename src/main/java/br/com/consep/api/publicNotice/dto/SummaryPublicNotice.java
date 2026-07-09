package br.com.consep.api.publicNotice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SummaryPublicNotice {
  private Long id;

  private String number;

  private String organizationName;

  // private LocalDate createdAt;

  // private LocalDate endAt;

  // @JsonIgnore
  // private Organization organization;
}
