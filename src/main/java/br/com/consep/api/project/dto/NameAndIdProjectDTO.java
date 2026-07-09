package br.com.consep.api.project.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NameAndIdProjectDTO {
  private Long id;
  private String name;
}
