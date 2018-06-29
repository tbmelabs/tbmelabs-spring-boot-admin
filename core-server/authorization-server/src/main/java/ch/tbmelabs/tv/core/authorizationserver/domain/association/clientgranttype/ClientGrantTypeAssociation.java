package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "client_has_grant_types")
@IdClass(ClientGrantTypeAssociationId.class)
public class ClientGrantTypeAssociation extends AbstractAuditingEntity {

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

  public void setGrantType(GrantType grantType) {
    this.clientGrantType = grantType;
    this.clientGrantTypeId = grantType.getId();
  }

  public void setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientGrantTypeAssociation other = (ClientGrantTypeAssociation) o;
    return clientId != null && clientGrantTypeId != null && clientId.equals(other.clientId)
        && clientGrantTypeId.equals(other.clientGrantTypeId);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
