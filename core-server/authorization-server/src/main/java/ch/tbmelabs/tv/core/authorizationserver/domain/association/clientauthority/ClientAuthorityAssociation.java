package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
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
@Table(name = "client_has_authorities")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(ClientAuthorityAssociationId.class)
public class ClientAuthorityAssociation extends NicelyDocumentedJDBCResource {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  @Column(name = "client_id")
  private Long clientId;

  @Id
  @NotNull
  @Column(name = "client_authority_id")
  private Long clientAuthorityId;

  @ManyToOne
  @JsonBackReference("client_has_authorities")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;

  @ManyToOne
  @JsonBackReference("authority_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_authority_id", referencedColumnName = "id")
  private Authority clientAuthority;

  public ClientAuthorityAssociation(Client client, Authority authority) {
    setClient(client);
    setClientAuthority(authority);
  }

  public void setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();
  }

  public void setClientAuthority(Authority authority) {
    this.clientAuthority = authority;
    this.clientAuthorityId = authority.getId();
  }

  public Client getClient() {
    return this.client;
  }

  public Authority getClientAuthority() {
    return this.clientAuthority;
  }
}
