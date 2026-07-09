package br.com.consep.api.toPay.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.consep.api.toPay.entity.ToPay;
import br.com.consep.api.shared.enums.ToPayStatus;

@Repository
public interface ToPayRepository extends JpaRepository<ToPay, Long>, JpaSpecificationExecutor<ToPay> {
  List<ToPay> findAllByProjectId(Long projectId);

  @Query("SELECT COALESCE(SUM(t.value), 0) FROM ToPay t WHERE t.project.id = :projectId AND t.status = 'PAID'")
  BigDecimal getSumByProjectId(@Param("projectId") Long projectId);

  List<ToPay> findByStatus(ToPayStatus status, Pageable pageable);
}
