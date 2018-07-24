package ch.tbmelabs.tv.core.authorizationserver.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user_roles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends AbstractAuditingEntity implements GrantedAuthority {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "user_roles_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Size(max = 16)
  private String name;

  @JsonManagedReference("role_has_users")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "role", cascade = {CascadeType.ALL})
  private Set<UserRoleAssociation> usersWithRoles;

  public Role(String authority) {
    setName(authority);
  }

  @Override
  public String getAuthority() {
    return getName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Role other = (Role) o;
    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
