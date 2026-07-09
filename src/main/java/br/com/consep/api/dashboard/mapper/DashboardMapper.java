package br.com.consep.api.dashboard.mapper;

import br.com.consep.api.dashboard.dto.ToPayDashboardDto;
import br.com.consep.api.toPay.entity.ToPay;

public class DashboardMapper {
  public static ToPayDashboardDto toPayDashboardDto(ToPay request) {
    if (request == null) {
      return null;
    }

    return new ToPayDashboardDto(request.getDocument().getOriginalName(), request.getId(), request.getValue(),
        request.getDueDate(), request.getStatus());
  }
}
