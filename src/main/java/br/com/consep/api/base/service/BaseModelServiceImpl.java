package br.com.consep.api.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.consep.api.base.BaseModel;
import br.com.consep.api.base.repository.BaseModelRepository;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BaseModelServiceImpl implements BaseModelService {

  private final BaseModelRepository baseModelRepository;

  @Override
  public BaseModel findById(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Argumento inválido");
    }

    return baseModelRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Argumento inválido"));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    baseModelRepository.deleteById(id);
    baseModelRepository.flush();
  }
}
