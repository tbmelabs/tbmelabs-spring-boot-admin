package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
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

  @JsonBackReference("client")
  @JoinColumn(insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @PrimaryKeyJoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;

  @JsonBackReference("clientGrantType")
  @JoinColumn(name = "client_grant_type_id", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @PrimaryKeyJoinColumn(name = "client_grant_type_id", referencedColumnName = "id")
  private GrantType clientGrantType;

  public ClientGrantTypeAssociation(Client client, GrantType grantType) {
    this.client = client;
    this.clientId = client.getId();
    this.clientGrantType = grantType;
    this.clientGrantTypeId = grantType.getId();
  }

  public ClientGrantTypeAssociation setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();

    return this;
  }

  public ClientGrantTypeAssociation setGrantType(GrantType grantType) {
    this.clientGrantType = grantType;
    this.clientGrantTypeId = grantType.getId();

    return this;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return super.toString();
    }
  }
}