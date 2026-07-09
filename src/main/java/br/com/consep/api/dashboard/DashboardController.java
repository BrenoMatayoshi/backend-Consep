package br.com.consep.api.dashboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.dashboard.dto.DashboardPageDto;
import br.com.consep.api.dashboard.service.DashboardService;
import br.com.consep.api.shared.PathConstant;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = PathConstant.url + "/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<DashboardPageDto> getDashboardData() {
    return ResponseEntity.ok(dashboardService.getDashboardData());
  }

}
