package ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_has_roles")
@IdClass(UserRoleAssociationId.class)
public class UserRoleAssociation extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference("user_has_roles")
  @LazyCollection(LazyCollectionOption.FALSE)
  private User user;

  @ManyToOne
  @JoinColumn(name = "user_role_id")
  @JsonBackReference("role_has_users")
  @LazyCollection(LazyCollectionOption.FALSE)
  private Role role;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserRoleAssociation other = (UserRoleAssociation) o;
    return user != null && user.equals(other.user) && role != null
        && role.equals(other.role);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(user.getId() + HASH_CODE_SEPARATOR + role.getId());
  }
}
