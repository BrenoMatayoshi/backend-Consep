package br.com.consep.api.toPay.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.enums.ToPayStatus;
import br.com.consep.api.toPay.dto.RequestToPay;
import br.com.consep.api.toPay.dto.SummaryToPay;
import br.com.consep.api.toPay.dto.UpdateStatusToPay;
import br.com.consep.api.toPay.entity.ToPay;

public interface ToPayService {
  SummaryToPay createToPay(RequestToPay request, Long projectId);

  List<SummaryToPay> getAllByProjectId(Long projectId);

  BigDecimal getValueByProject(Long projectId);

  SummaryToPay updateToPayStatus(Long id, UpdateStatusToPay newStatus);

  ToPay findById(Long id);

  List<ToPay> findByStatus(ToPayStatus status, int limit);

  PagedResponseDTO<?> findAll(
      int page,
      int limit,
      BigDecimal value,
      LocalDate dueDate,
      ToPayStatus status,
      String sortBy,
      String sortOrder);

  void deleteToPay(Long id);
}