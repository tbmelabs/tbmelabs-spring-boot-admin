package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
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

  public Client getClient() {
    return this.client;
  }

  public void setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();
  }

  public Authority getClientAuthority() {
    return this.clientAuthority;
  }

  public void setClientAuthority(Authority authority) {
    this.clientAuthority = authority;
    this.clientAuthorityId = authority.getId();
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof ClientAuthorityAssociation)) {
      return false;
    }

    ClientAuthorityAssociation other = (ClientAuthorityAssociation) object;
    if (other.getClient() == null || other.getClientAuthority() == null) {
      return false;
    }

    return Objects.equals(this.getClient(), other.getClient())
        && Objects.equals(this.getClientAuthority(), other.getClientAuthority());
  }

  @Override
  public int hashCode() {
    if (this.getClient() == null || this.getClient().getId() == null
        || this.getClientAuthority() == null || this.getClientAuthority().getId() == null) {
      return super.hashCode();
    }

    // @formatter:off
    return new HashCodeBuilder()
        .append(this.getClient().getId())
        .append(this.getClientAuthority().getId())
        .build();
    // @formatter:on
  }
}
