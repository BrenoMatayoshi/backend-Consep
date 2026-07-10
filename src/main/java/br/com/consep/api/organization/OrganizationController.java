package br.com.consep.api.organization;

import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.organization.dto.DetailedOrganization;
import br.com.consep.api.organization.dto.RequestOrganization;
import br.com.consep.api.organization.dto.SummaryOrganization;
import br.com.consep.api.organization.service.OrganizationService;
import br.com.consep.api.shared.PathConstant;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathConstant.url + "/organization")
public class OrganizationController {

  private final OrganizationService organizationService;

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PagedResponseDTO<?>> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int limit,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String abbreviation,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortOrder) {
    return ResponseEntity.ok(organizationService.findAll(page, limit, name, abbreviation, sortBy, sortOrder));
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<SummaryOrganization> createOrganization(@RequestBody @Valid RequestOrganization request) {
    SummaryOrganization organization = organizationService.createOrganization(request);

    URI uri = URI.create("/organization/" + organization.getId());

    return ResponseEntity.created(uri).body(organization);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailedOrganization> getMethodName(@PathVariable Long id) {
    return ResponseEntity.ok(organizationService.getOrganization(id));
  }

  @DeleteMapping("/{id}")
  public void deleteOrganization(@PathVariable Long id) {
    organizationService.deleteOrganization(id);
  }
}
