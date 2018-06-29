package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractAuditingEntity {

  @Transient
  public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "users_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Size(min = 5, max = 64)
  @Column(unique = true)
  private String username;

  @NotEmpty
  @Email
  @Size(max = 128)
  @Column(unique = true)
  private String email;

  @NotEmpty
  @Size(min = 60, max = 60)
  @Column(columnDefinition = "bpchar(60)")
  private String password;

  @Transient
  private String confirmation;

  @NotNull
  private Boolean isEnabled = false;

  @NotNull
  private Boolean isBlocked = false;

  @JsonBackReference
  @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL})
  private EmailConfirmationToken emailConfirmationToken;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference("user_has_roles")
  @LazyCollection(LazyCollectionOption.FALSE)
  private Set<UserRoleAssociation> roles;

  @PrePersist
  public void onCreate() {
    this.setPassword(PASSWORD_ENCODER.encode(this.getPassword()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User other = (User) o;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
