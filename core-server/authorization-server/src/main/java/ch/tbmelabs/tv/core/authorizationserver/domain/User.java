package ch.tbmelabs.tv.core.authorizationserver.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Objects;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
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
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
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
  public boolean equals(Object object) {
    if (object == null || !(object instanceof User)) {
      return false;
    }

    User other = (User) object;
    return Objects.equals(this.getId(), other.getId())
        && Objects.equals(this.getUsername(), other.getUsername())
        && Objects.equals(this.getEmail(), other.getEmail())
        && Objects.equals(this.getPassword(), other.getPassword())
        && Objects.equals(this.getIsEnabled(), other.getIsEnabled())
        && Objects.equals(this.getIsBlocked(), other.getIsBlocked());
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
