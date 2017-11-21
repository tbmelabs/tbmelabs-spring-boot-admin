package ch.tbmelabs.tv.shared.domain.authentication.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.tbmelabs.tv.shared.domain.authentication.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.shared.domain.authentication.association.userrole.UserRoleAssociation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  @Transient
  @JsonIgnore
  public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "users_id_seq"), @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 64)
  @Column(unique = true)
  private String username;

  @NotEmpty
  @Length(max = 128)
  private String email;

  @NotEmpty
  @Length(max = 60, min = 60)
  @Column(columnDefinition = "bpchar(60)")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NotNull
  private Boolean isEnabled;

  @NotNull
  private Boolean isBlocked;

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE }, mappedBy = "userId")
  private Collection<UserRoleAssociation> grantedAuthorities;

  @Override
  @PrePersist
  public void onCreate() {
    super.onCreate();

    password = PASSWORD_ENCODER.encode(password);
  }

  public Collection<UserRoleAssociation> rolesToAssociations(List<Role> roleList) {
    if (roleList == null || roleList.size() == 0) {
      return null;
    }

    List<UserRoleAssociation> convertedRoles = new ArrayList<>();

    roleList.forEach(role -> {
      convertedRoles.add(new UserRoleAssociation(id, role.getId(), this, role));
    });

    return convertedRoles;
  }
}