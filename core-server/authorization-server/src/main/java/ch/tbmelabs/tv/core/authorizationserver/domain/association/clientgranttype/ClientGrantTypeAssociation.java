package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
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
@Table(name = "client_has_grant_types")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(ClientGrantTypeAssociationId.class)
public class ClientGrantTypeAssociation extends NicelyDocumentedJDBCResource {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  @Column(name = "client_id")
  private Long clientId;

  @Id
  @NotNull
  @Column(name = "client_grant_type_id")
  private Long clientGrantTypeId;

  @ManyToOne
  @JsonBackReference("client_has_grant_types")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;

  @ManyToOne
  @JsonBackReference("grant_type_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "client_grant_type_id", insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_grant_type_id", referencedColumnName = "id")
  private GrantType clientGrantType;

  public ClientGrantTypeAssociation(Client client, GrantType grantType) {
    setClient(client);
    setGrantType(grantType);
  }

  public void setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();
  }

  public void setGrantType(GrantType grantType) {
    this.clientGrantType = grantType;
    this.clientGrantTypeId = grantType.getId();
  }

  public Client getClient() {
    return this.client;
  }

  public GrantType getClientGrantType() {
    return this.clientGrantType;
  }
}
