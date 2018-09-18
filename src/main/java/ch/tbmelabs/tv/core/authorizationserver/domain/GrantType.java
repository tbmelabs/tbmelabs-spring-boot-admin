package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.HashSet;
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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "client_grant_types")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GrantType extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "client_grant_types_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Size(max = 32)
  private String name;

  @JsonProperty(access = Access.WRITE_ONLY)
  @JsonManagedReference("grant_type_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "grantType", cascade = {CascadeType.ALL})
  private Set<ClientGrantTypeAssociation> clientsWithGrantTypes = new HashSet<>();

  public GrantType(String name) {
    setName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GrantType other = (GrantType) o;
    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
