package ch.tbmelabs.tv.core.authorizationserver.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@EqualsAndHashCode
@JsonIgnoreProperties
@Table(name = "client_authorities")
public class Authority extends AbstractAuditingEntity implements GrantedAuthority {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "client_authorities_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Size(max = 16)
  private String name;

  @JsonProperty(access = Access.WRITE_ONLY)
  @JsonManagedReference("authority_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "clientAuthority")
  private Set<ClientAuthorityAssociation> clientsWithAuthorities;

  // TODO: This is some serious bug! maven-compiler-plugin does not behave correctly to lombok.
  // If there is an existing constructor "constructor is already defined"
  public Authority() {

  }

  public Authority(String name) {
    setName(name);
  }

  @Override
  public String getAuthority() {
    return getName();
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof Authority)) {
      return false;
    }

    Authority other = (Authority) object;
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
