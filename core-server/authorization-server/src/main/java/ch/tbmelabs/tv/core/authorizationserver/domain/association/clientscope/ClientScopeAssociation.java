package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
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
@Table(name = "client_has_scopes")
@IdClass(ClientScopeAssociationId.class)
public class ClientScopeAssociation extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  @Column(name = "client_id")
  private Long clientId;

  @Id
  @NotNull
  @Column(name = "client_scope_id")
  private Long clientScopeId;

  @ManyToOne
  @JsonBackReference("client_has_scopes")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;

  @ManyToOne
  @JsonBackReference("scope_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_scope_id", referencedColumnName = "id")
  private Scope clientScope;

  public ClientScopeAssociation(Client client, Scope scope) {
    setClient(client);
    setClientScope(scope);
  }

  public void setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();
  }

  public void setClientScope(Scope scope) {
    this.clientScope = scope;
    this.clientScopeId = scope.getId();
  }

  public Client getClient() {
    return this.client;
  }

  public Scope getClientScope() {
    return this.clientScope;
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof ClientScopeAssociation)) {
      return false;
    }

    ClientScopeAssociation other = (ClientScopeAssociation) object;
    if (other.getClient() == null || other.getClientScope() == null) {
      return false;
    }

    return Objects.equals(this.getClient(), other.getClient())
        && Objects.equals(this.getClientScope(), other.getClientScope());
  }

  @Override
  public int hashCode() {
    if (this.getClient() == null || this.getClient().getId() == null
        || this.getClientScope() == null || this.getClientScope().getId() == null) {
      return super.hashCode();
    }

    // @formatter:off
    return new HashCodeBuilder()
        .append(this.getClient().getId())
        .append(this.getClientScope().getId())
        .build();
    // @formatter:on
  }
}
