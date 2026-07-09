package br.com.consep.api.user.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.consep.api.user.entity.User;

public class UserSpecifications {
  public static Specification<User> nameFilter(String name) {
    return (root, query, cb) -> (name == null || name.isBlank())
        ? cb.conjunction()
        : cb.like(cb.lower(root.get("username")), "%" + name.toLowerCase() + "%");
  }
}