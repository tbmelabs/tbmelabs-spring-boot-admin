package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

import java.util.Objects;
import javax.persistence.CascadeType;
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
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_has_grant_types")
@IdClass(ClientGrantTypeAssociationId.class)
public class ClientGrantTypeAssociation extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @JoinColumn(name = "client_id")
  @JsonBackReference("client_has_grant_types")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Client client;

  @JoinColumn(name = "client_grant_type_id")
  @JsonBackReference("grant_type_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  private GrantType grantType;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientGrantTypeAssociation other = (ClientGrantTypeAssociation) o;
    return client != null && client.equals(other.client) && grantType != null
        && grantType.equals(other.grantType);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(client.getId() + HASH_CODE_SEPARATOR + grantType.getId());
  }
}
