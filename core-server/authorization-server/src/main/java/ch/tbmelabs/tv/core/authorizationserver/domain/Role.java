package ch.tbmelabs.tv.core.authorizationserver.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "user_roles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends AuditingEntity implements GrantedAuthority {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "user_roles_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 16)
  private String name;

  @JsonManagedReference("role_has_users")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "userRoleId")
  private Set<UserRoleAssociation> usersWithRoles;

  public Role(String authority) {
    setName(authority);
  }

  @Override
  public String getAuthority() {
    return getName();
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof Role)) {
      return false;
    }

    Role other = (Role) object;
    return Objects.equals(this.getId(), other.getId())
        && Objects.equals(this.getName(), other.getName());
  }

  @Override
  public int hashCode() {
    if (this.getId() == null) {
      return super.hashCode();
    }

    // @formatter:off
    return new HashCodeBuilder()
        .append(this.getId())
        .build();
    // @formatter:on
  }
}
