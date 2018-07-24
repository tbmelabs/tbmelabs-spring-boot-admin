package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_has_authorities")
@IdClass(ClientAuthorityAssociationId.class)
public class ClientAuthorityAssociation extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @JoinColumn(name = "client_id")
  @JsonBackReference("client_has_authorities")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Client client;

  @Id
  @JoinColumn(name = "client_authority_id")
  @JsonBackReference("authority_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Authority authority;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientAuthorityAssociation other = (ClientAuthorityAssociation) o;
    return client != null && client.equals(other.client) && authority != null
        && authority.equals(other.authority);
  }

  @Override
  public int hashCode() {
    if (client == null || client.getId() == null) {
      return Objects.hashCode(authority.getId());
    } else if (authority == null || authority.getId() == null) {
      return Objects.hashCode(client.getId());
    }

    return Objects.hashCode(client.getId() + HASH_CODE_SEPARATOR + authority.getId());
  }
}
