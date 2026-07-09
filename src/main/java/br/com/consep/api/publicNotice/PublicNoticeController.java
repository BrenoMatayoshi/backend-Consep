package br.com.consep.api.publicNotice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.publicNotice.dto.RequestPublicNotice;
import br.com.consep.api.publicNotice.dto.SummaryPublicNotice;
import br.com.consep.api.publicNotice.service.PublicNoticeService;
import br.com.consep.api.shared.PathConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathConstant.url + "/publicNotice")
public class PublicNoticeController {

  private final PublicNoticeService publicNoticeService;

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<SummaryPublicNotice>> findAll() {
    return ResponseEntity.ok().body(publicNoticeService.findAll());
  }

  @PostMapping("/organization/{organizationId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<SummaryPublicNotice> createPublicNotice(@RequestBody @Valid RequestPublicNotice request,
      @PathVariable Long organizationId) {
    return ResponseEntity.ok().body(publicNoticeService.createPublicNotice(request, organizationId));
  }

}
