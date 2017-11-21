package ch.tbmelabs.tv.shared.domain.authentication.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.tbmelabs.tv.shared.domain.authentication.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.shared.domain.authentication.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.shared.domain.authentication.association.clientrole.ClientAuthorityAssociation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "clients")
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

  @JoinColumn(name = "client_scope_id", referencedColumnName = "id")
  @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
  private Scope scope;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "clientId")
  private Collection<ClientGrantTypeAssociation> grantTypes;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "clientId")
  private Collection<ClientAuthorityAssociation> grantedAuthorities;

  public Collection<ClientGrantTypeAssociation> grantTypesToAssociations(List<GrantType> grantTypeList) {
    List<ClientGrantTypeAssociation> convertedGrantTypes = new ArrayList<>();

    grantTypeList.forEach(grantType -> {
      convertedGrantTypes.add(new ClientGrantTypeAssociation(id, grantType.getId(), this, grantType));
    });

    return convertedGrantTypes;
  }

  public Collection<ClientAuthorityAssociation> authoritiesToAssociations(List<Authority> authorityList) {
    List<ClientAuthorityAssociation> convertedAuthorities = new ArrayList<>();

    authorityList.forEach(authority -> {
      convertedAuthorities.add(new ClientAuthorityAssociation(id, authority.getId(), this, authority));
    });

    return convertedAuthorities;
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
    Set<String> convertedAuthorizedGrantTypes = new HashSet<>();

    grantTypes.forEach(association -> {
      convertedAuthorizedGrantTypes.add(association.getGrantType().getName());
    });

    return convertedAuthorizedGrantTypes;
  }

  @Override
  public Set<String> getScope() {
    return new HashSet<>(Arrays.asList(scope.getName()));
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return new HashSet<String>(Arrays.asList(new String[] { this.redirectUri }));
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    grantedAuthorities.forEach(association -> {
      authorities.add(association.getClientAuthority());
    });

    return authorities;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    // TODO
    return new HashMap<>();
  }
}