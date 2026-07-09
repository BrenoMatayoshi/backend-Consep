package br.com.consep.api.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.shared.PathConstant;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.user.dto.ChangeRoleUserDTO;
import br.com.consep.api.user.dto.RequestUser;
import br.com.consep.api.user.dto.SummaryUser;
import br.com.consep.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = PathConstant.url + "/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PagedResponseDTO<?>> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int limit,
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortOrder) {
    return ResponseEntity.ok(userService.findAll(page, limit, name, sortBy, sortOrder));
  }

  @PostMapping("/organization/{organizationId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<SummaryUser> createUser(@RequestBody @Valid RequestUser request,
      @PathVariable Long organizationId) {
    SummaryUser user = userService.createUser(request, organizationId);
    URI uri = URI.create("/" + user.getId());
    return ResponseEntity.created(uri).body(user);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<SummaryUser> findUserProfile(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findUserProfile(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<SummaryUser> updateUserRole(@PathVariable Long id,
      @Valid @RequestBody ChangeRoleUserDTO updateStatusRole) {
    return ResponseEntity.ok(userService.updateUserRole(id, updateStatusRole));
  }
}
