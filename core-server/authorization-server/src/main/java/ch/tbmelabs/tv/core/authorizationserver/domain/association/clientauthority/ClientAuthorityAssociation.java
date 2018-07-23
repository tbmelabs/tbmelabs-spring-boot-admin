package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority;

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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "client_has_authorities")
@IdClass(ClientAuthorityAssociationId.class)
public class ClientAuthorityAssociation extends AbstractAuditingEntity {

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientAuthorityAssociation other = (ClientAuthorityAssociation) o;
    return clientId != null && clientAuthorityId != null && clientId.equals(other.clientId)
        && clientAuthorityId.equals(other.clientAuthorityId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(client.getId() + HASH_CODE_SEPARATOR + clientAuthority.getId());
  }
}
