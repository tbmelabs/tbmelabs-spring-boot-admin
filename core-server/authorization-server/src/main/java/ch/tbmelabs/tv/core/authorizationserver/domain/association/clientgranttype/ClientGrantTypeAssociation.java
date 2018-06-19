package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
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

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof ClientGrantTypeAssociation)) {
      return false;
    }

    ClientGrantTypeAssociation other = (ClientGrantTypeAssociation) object;
    if (other.getClient() == null || other.getClientGrantType() == null) {
      return false;
    }

    return Objects.equals(this.getClient(), other.getClient())
        && Objects.equals(this.getClientGrantType(), other.getClientGrantType());
  }

  @Override
  public int hashCode() {
    if (this.getClient() == null || this.getClient().getId() == null
        || this.getClientGrantType() == null || this.getClientGrantType().getId() == null) {
      return super.hashCode();
    }

    // @formatter:off
    return new HashCodeBuilder()
        .append(this.getClient().getId())
        .append(this.getClientGrantType().getId())
        .build();
    // @formatter:on
  }
}
