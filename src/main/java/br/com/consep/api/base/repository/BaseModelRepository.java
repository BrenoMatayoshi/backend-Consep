package br.com.consep.api.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.consep.api.base.BaseModel;

@Repository
public interface BaseModelRepository extends JpaRepository<BaseModel, Long> {

}
