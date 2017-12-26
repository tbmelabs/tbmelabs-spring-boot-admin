package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "clients")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends NicelyDocumentedJDBCResource implements ClientDetails {
  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "clients_id_seq"), @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 36, min = 36)
  @Column(name = "client_id", columnDefinition = "bpchar(36)", unique = true)
  private String clientId;

  @Length(max = 36, min = 36)
  @Column(columnDefinition = "bpchar(36)")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String secret;

  @NotNull
  @Column(name = "secret_required")
  private Boolean isSecretRequired;

  @NotNull
  @Column(name = "auto_approve")
  private Boolean isAutoApprove;

  @NotNull
  @Column(name = "access_token_validity")
  private Integer accessTokenValiditySeconds;

  @NotNull
  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValiditySeconds;

  @Length(max = 256)
  private String redirectUri;

  @JsonManagedReference("client")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "clientId")
  private Collection<ClientGrantTypeAssociation> grantTypes;

  @JsonManagedReference("client")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "clientId")
  private Collection<ClientAuthorityAssociation> grantedAuthorities;

  @JsonManagedReference("client")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "clientId")
  private Collection<ClientScopeAssociation> scopes;

  public Collection<ClientGrantTypeAssociation> grantTypesToAssociations(List<GrantType> grantTypeList) {
    return grantTypeList.stream().map(grantType -> new ClientGrantTypeAssociation(this, grantType))
        .collect(Collectors.toList());
  }

  public Collection<ClientAuthorityAssociation> authoritiesToAssociations(List<Authority> authorityList) {
    return authorityList.stream().map(authority -> new ClientAuthorityAssociation(this, authority))
        .collect(Collectors.toList());
  }

  @Override
  public String getClientId() {
    return this.clientId;
  }

  @Override
  public Set<String> getResourceIds() {
    // TODO
    return new HashSet<>();
  }

  @Override
  public boolean isSecretRequired() {
    return this.isSecretRequired.booleanValue();
  }

  @Override
  public boolean isAutoApprove(String scope) {
    return this.isAutoApprove.booleanValue();
  }

  @Override
  public String getClientSecret() {
    return this.secret;
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return this.accessTokenValiditySeconds;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return this.refreshTokenValiditySeconds;
  }

  @Override
  public boolean isScoped() {
    return !getScope().isEmpty();
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return grantTypes.stream().map(association -> association.getClientGrantType().getName())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<String> getScope() {
    return scopes.stream().map(association -> association.getClientScope().getName()).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return new HashSet<String>(Arrays.asList(this.redirectUri));
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return grantedAuthorities.stream().map(association -> association.getClientAuthority())
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    // TODO
    return new HashMap<>();
  }
}