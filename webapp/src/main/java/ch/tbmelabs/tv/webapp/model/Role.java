package ch.tbmelabs.tv.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "access_level")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class Role extends NicelyDocumentedEntity {
  @NotEmpty
  @Column(name = "role_name", columnDefinition = "VARCHAR(32)")
  private String roleName;

  // @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE },
  // mappedBy = "accessLevel")
  // @JsonIgnoreProperties({ "accessLevel" })
  // private Set<Account> accounts;
}