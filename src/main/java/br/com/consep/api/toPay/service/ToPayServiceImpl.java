package br.com.consep.api.toPay.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.consep.api.project.entity.Project;
import br.com.consep.api.project.service.ProjectService;
import br.com.consep.api.shared.dto.MetaDTO;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.enums.ToPayStatus;
import br.com.consep.api.shared.exception.ElementNotFoundException;
import br.com.consep.api.toPay.dto.RequestToPay;
import br.com.consep.api.toPay.dto.SummaryToPay;
import br.com.consep.api.toPay.dto.UpdateStatusToPay;
import br.com.consep.api.toPay.entity.ToPay;
import br.com.consep.api.toPay.mapper.ToPayMapper;
import br.com.consep.api.toPay.repository.ToPayRepository;
import br.com.consep.api.toPay.specification.ToPaySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ToPayServiceImpl implements ToPayService {

  private final ToPayRepository toPayRepository;

  private final ProjectService projectService;

  @Override
  public SummaryToPay createToPay(RequestToPay request, Long projectId) {
    Project project = projectService.findById(projectId);

    ToPay toPay = ToPayMapper.toEntity(request);

    toPay.setProject(project);

    return ToPayMapper.toSummary(toPayRepository.save(toPay));
  }

  @Override
  public List<SummaryToPay> getAllByProjectId(Long projectId) {
    return toPayRepository.findAllByProjectId(projectId)
        .stream()
        .map(toPay -> {
          SummaryToPay summary = ToPayMapper.toSummary(toPay);
          return summary;
        })
        .toList();
  }

  @Override
  public BigDecimal getValueByProject(Long projectId) {
    return toPayRepository.getSumByProjectId(projectId);
  }

  @Override
  public SummaryToPay updateToPayStatus(Long id, UpdateStatusToPay newStatus) {
    if (id == null) {
      throw new ElementNotFoundException("Boleto não encontrado");
    }
    ToPay toPay = findById(id);
    toPay.setStatus(newStatus.getStatus());
    return ToPayMapper.toSummary(toPayRepository.save(toPay));
  }

  @Override
  public ToPay findById(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Boleto não encontrado");
    }
    return toPayRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Boleto não encontrado"));
  }

  @Override
  public List<ToPay> findByStatus(ToPayStatus status, int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return toPayRepository.findByStatus(status, pageable);
  }

  @Override
  public PagedResponseDTO<?> findAll(
      int page,
      int limit,

      BigDecimal value,
      LocalDate dueDate,
      ToPayStatus status,

      String sortBy,
      String sortOrder) {
    String sortProperty = resolveSortBy(sortBy);
    Sort sort = sortOrder.equalsIgnoreCase("desc")
        ? Sort.by(sortProperty).descending()
        : Sort.by(sortProperty).ascending();
    Pageable pageable = PageRequest.of(page, limit, sort);

    Specification<ToPay> spec = Specification
        .<ToPay>unrestricted()
        .and(ToPaySpecification.dueDateFilter(dueDate))
        .and(ToPaySpecification.toPayStatusFilter(status))
        .and(ToPaySpecification.valueFilter(value));

    Page<ToPay> toPayPage = toPayRepository.findAll(spec, pageable);

    Page<SummaryToPay> usuarioPage = toPayPage.map(ToPayMapper::toSummary);

    MetaDTO meta = new MetaDTO(usuarioPage.getTotalElements(), usuarioPage.getTotalPages());
    return new PagedResponseDTO<>(usuarioPage.getContent(), meta);
  }

  private String resolveSortBy(String sortBy) {
    return switch (sortBy) {
      case "paymentSlip", "paymentProof" -> "payment.typeDocument";
      default -> sortBy;
    };
  }

  @Override
  @Transactional
  public void deleteToPay(Long id) {
    if (id == null) {
      throw new ElementNotFoundException("Boleto não encontrado");
    }

    ToPay toPay = Objects.requireNonNull(findById(id));

    toPayRepository.delete(toPay);
    toPayRepository.flush();
  }
}
