package ch.tbmelabs.tv.core.authorizationserver.service.clientdetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class ClientDetailsImpl implements ClientDetails {

  public static final String CLIENT_REDIRECT_URI_SPLITTERATOR = ",";
  private static final long serialVersionUID = 1L;
  private Client client;

  public ClientDetailsImpl(Client client) {
    this.client = client;
  }

  @Override
  public String getClientId() {
    return client.getClientId();
  }

  @Override
  public Set<String> getResourceIds() {
    // TODO: What is this?
    return new HashSet<>();
  }

  @Override
  public boolean isSecretRequired() {
    return client.getIsSecretRequired();
  }

  @Override
  public boolean isAutoApprove(String scope) {
    return client.getIsAutoApprove();
  }

  @Override
  public String getClientSecret() {
    return client.getSecret();
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return client.getAccessTokenValiditySeconds();
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return client.getRefreshTokenValiditySeconds();
  }

  @Override
  public boolean isScoped() {
    return !getScope().isEmpty();
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return client.getGrantTypes().stream()
        .map(association -> association.getGrantType().getName()).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getScope() {
    return client.getScopes().stream().map(association -> association.getScope().getName())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return new HashSet<>(
        Arrays.asList(client.getRedirectUri().split(CLIENT_REDIRECT_URI_SPLITTERATOR)));
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return client.getGrantedAuthorities().stream()
        .map(ClientAuthorityAssociation::getAuthority).collect(Collectors.toList());
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    // TODO: Is additional information required?
    return new HashMap<>();
  }
}
