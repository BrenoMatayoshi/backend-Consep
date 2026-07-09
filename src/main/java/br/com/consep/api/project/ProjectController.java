package br.com.consep.api.project;

import org.springframework.web.bind.annotation.RestController;

import br.com.consep.api.project.dto.RequestProject;
import br.com.consep.api.project.dto.SummaryProject;
import br.com.consep.api.project.dto.UpdateStatusProject;
import br.com.consep.api.project.dto.CompleteProject;
import br.com.consep.api.project.service.ProjectService;
import br.com.consep.api.shared.PathConstant;
import br.com.consep.api.shared.dto.PagedResponseDTO;
import br.com.consep.api.shared.enums.ProjectStatus;
import br.com.consep.api.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = PathConstant.url + "/project")
public class ProjectController {

  private final ProjectService projectService;

  @GetMapping("/all")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<PagedResponseDTO<?>> findAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int limit,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) BigDecimal value,
      @RequestParam(required = false) ProjectStatus projectStatus,
      @RequestParam(required = false) String noticeNumber,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @AuthenticationPrincipal User user) {
    return ResponseEntity.ok()
        .body(projectService.findAll(page, limit, name, value, projectStatus, noticeNumber, sortBy, sortOrder, user));
  }

  @PostMapping("/organization/{organizationId}/publicNotice/{publicNoticeId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CompleteProject> createProject(@RequestBody @Valid RequestProject project,
      @PathVariable Long organizationId, @PathVariable Long publicNoticeId) {
    return ResponseEntity.ok().body(projectService.createProject(project, organizationId, publicNoticeId));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<CompleteProject> getProject(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return ResponseEntity.ok().body(projectService.getProject(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<SummaryProject> updateProjectStatus(@PathVariable Long id,
      @Valid @RequestBody UpdateStatusProject updateStatusProject) {
    return ResponseEntity.ok(projectService.updateProjectStatus(id, updateStatusProject));
  }

}
