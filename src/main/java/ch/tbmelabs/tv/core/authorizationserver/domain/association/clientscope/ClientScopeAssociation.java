package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_has_scopes")
@IdClass(ClientScopeAssociationId.class)
public class ClientScopeAssociation extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @JoinColumn(name = "client_id")
  @JsonBackReference("client_has_scopes")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Client client;

  @Id
  @JoinColumn(name = "client_scope_id")
  @JsonBackReference("scope_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Scope scope;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientScopeAssociation other = (ClientScopeAssociation) o;
    return client != null && client.equals(other.client) && scope != null
      && scope.equals(other.scope);
  }

  @Override
  public int hashCode() {
    if (client == null || client.getId() == null) {
      return Objects.hashCode(scope.getId());
    } else if (scope == null || scope.getId() == null) {
      return Objects.hashCode(client.getId());
    }

    return Objects.hashCode(client.getId() + HASH_CODE_SEPARATOR + scope.getId());
  }
}
