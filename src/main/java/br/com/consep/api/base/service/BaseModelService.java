package br.com.consep.api.base.service;

import br.com.consep.api.base.BaseModel;

public interface BaseModelService {
  BaseModel findById(Long id);

  void deleteById(Long id);
}
