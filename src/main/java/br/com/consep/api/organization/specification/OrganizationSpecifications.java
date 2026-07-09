package br.com.consep.api.organization.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.consep.api.organization.entity.Organization;

public class OrganizationSpecifications {
  public static Specification<Organization> nameFilter(String name) {
    return (root, query, cb) -> (name == null || name.isBlank())
        ? cb.conjunction()
        : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  public static Specification<Organization> abbreviationFilter(String abbreviation) {
    return (root, query, cb) -> (abbreviation == null || abbreviation.isBlank())
        ? cb.conjunction()
        : cb.like(cb.lower(root.get("abbreviation")), "%" + abbreviation.toLowerCase() + "%");
  }
}
