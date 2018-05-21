package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "client_has_scopes")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(ClientScopeAssociationId.class)
public class ClientScopeAssociation extends NicelyDocumentedJDBCResource {

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
}
