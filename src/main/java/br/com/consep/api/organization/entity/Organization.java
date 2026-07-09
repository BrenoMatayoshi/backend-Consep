package br.com.consep.api.organization.entity;

import java.util.List;

import org.hibernate.annotations.Formula;

import br.com.consep.api.project.entity.Project;
import br.com.consep.api.publicNotice.entity.PublicNotice;
import br.com.consep.api.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "organization")
public class Organization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "abbreviation")
  private String abbreviation;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  private List<PublicNotice> notices;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  private List<Project> projects;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  private List<User> users;

  @Formula("(SELECT COUNT(u.id) FROM users u WHERE u.organization_id = id)")
  private Integer usersCount;

  @Formula("(SELECT COUNT(p.id) FROM project p WHERE p.organization_id = id)")
  private Integer projectsCount;

  @Formula("(SELECT COUNT(pn.id) FROM public_notice pn WHERE pn.organization_id = id)")
  private Integer noticesCount;
}
