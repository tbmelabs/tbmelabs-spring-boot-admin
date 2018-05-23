package ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@NoArgsConstructor
@Table(name = "user_has_roles")
@IdClass(UserRoleAssociationId.class)
public class UserRoleAssociation extends NicelyDocumentedJDBCResource {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  @Column(name = "user_id")
  private Long userId;

  @Id
  @NotNull
  @Column(name = "user_role_id")
  private Long userRoleId;

  @ManyToOne
  @JsonBackReference("user_has_roles")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JsonBackReference("role_has_users")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "user_role_id", referencedColumnName = "id")
  private Role userRole;

  public UserRoleAssociation(User user, Role role) {
    setUser(user);
    setUserRole(role);
  }

  public void setUser(User user) {
    this.user = user;
    this.userId = user.getId();
  }

  public void setUserRole(Role role) {
    this.userRole = role;
    this.userRoleId = role.getId();
  }

  public User getUser() {
    return this.user;
  }

  public Role getUserRole() {
    return this.userRole;
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof UserRoleAssociation)) {
      return false;
    }

    UserRoleAssociation other = (UserRoleAssociation) object;
    if (other.getUser() == null || other.getUserRole() == null) {
      return false;
    }

    return Objects.equals(this.getUser(), other.getUser())
        && Objects.equals(this.getUserRole(), other.getUserRole());
  }

  @Override
  public int hashCode() {
    if (this.getUser() == null || this.getUser().getId() == null
        || this.getUserRole() == null || this.getUserRole().getId() == null) {
      return super.hashCode();
    }

    // @formatter:off
    return new HashCodeBuilder()
        .append(this.getUser().getId())
        .append(this.getUserRole().getId())
        .build();
    // @formatter:on
  }
}
