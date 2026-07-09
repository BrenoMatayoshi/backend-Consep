package br.com.consep.api.toPay;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.shared.PathConstant;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.enums.ToPayStatus;
import br.com.consep.api.shared.enums.TypePayment;
import br.com.consep.api.toPay.dto.RequestToPay;
import br.com.consep.api.toPay.dto.SummaryToPay;
import br.com.consep.api.toPay.dto.UpdateStatusToPay;
import br.com.consep.api.toPay.service.ToPayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathConstant.url + "/toPay")
public class ToPayController {

  private final ToPayService toPayService;

  @PostMapping("/project/{projectId}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<SummaryToPay> createToPay(@Valid @RequestBody RequestToPay requestToPay,
      @PathVariable Long projectId) {

    requestToPay.setTypePayment(TypePayment.INVOICE);
    return ResponseEntity.ok().body(toPayService.createToPay(requestToPay, projectId));
  }

  @GetMapping("/project/{projectId}/all")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<SummaryToPay>> getAllByProject(
      @PathVariable Long projectId) {
    return ResponseEntity.ok(toPayService.getAllByProjectId(projectId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<SummaryToPay> changeStatus(@PathVariable Long id,
      @Valid @RequestBody UpdateStatusToPay updateStatusToPay) {
    return ResponseEntity.ok(toPayService.updateToPayStatus(id, updateStatusToPay));
  }

  @GetMapping("/all")
  public ResponseEntity<PagedResponseDTO<?>> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int limit,
      @RequestParam(required = false) BigDecimal value,
      @RequestParam(required = false) LocalDate dueDate,
      @RequestParam(required = false) ToPayStatus status,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortOrder) {
    return ResponseEntity.ok()
        .body(toPayService.findAll(page, limit, value, dueDate, status, sortBy, sortOrder));
  }

}
