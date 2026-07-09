package br.com.consep.api.project.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.com.consep.api.project.entity.Project;
import br.com.consep.api.publicNotice.entity.PublicNotice;
import br.com.consep.api.shared.enums.ProjectStatus;
import jakarta.persistence.criteria.Join;

public class ProjectSpecifications {
  public static Specification<Project> nameFilter(String name) {
    return (root, query, cb) -> (name == null || name.isBlank())
        ? cb.conjunction()
        : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  public static Specification<Project> valueFilter(BigDecimal value) {
    return (root, query, cb) -> value == null
        ? cb.conjunction()
        : cb.equal(root.get("value"), value);
  }

  public static Specification<Project> projectStatusFilter(ProjectStatus projectStatus) {
    return (root, query, cb) -> projectStatus == null
        ? cb.conjunction()
        : cb.equal(root.get("projectStatus"), projectStatus);
  }

  public static Specification<Project> noticeNumberFilter(String noticeNumber) {
    return (root, query, cb) -> {
      if (noticeNumber == null || noticeNumber.isBlank()) {
        return cb.conjunction();
      }

      Join<Project, PublicNotice> noticeJoin = root.join("notice");

      return cb.like(cb.lower(noticeJoin.get("number")), "%" + noticeNumber.toLowerCase() + "%");
    };
  }

  public static Specification<Project> organizationId(Long organizationId) {
    return (root, query, cb) -> {
      if (organizationId == null) {
        return cb.conjunction();
      }

      return cb.equal(root.get("organization").get("id"), organizationId);
    };
  }
}