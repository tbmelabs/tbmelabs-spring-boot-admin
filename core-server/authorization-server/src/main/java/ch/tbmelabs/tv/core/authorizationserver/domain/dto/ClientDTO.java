package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends AbstractBasicEntityDTO {

  private String clientId;
  private Boolean isSecretRequired = true;
  private Boolean isAutoApprove = false;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
  private String[] redirectUris;
  private Collection<GrantTypeDTO> grantTypes;
  private Collection<AuthorityDTO> grantedAuthorities;
  private Collection<ScopeDTO> scopes;

  public ClientDTO(Client client, ArrayList<GrantTypeDTO> grantTypes,
      ArrayList<AuthorityDTO> grantedAuthorities, ArrayList<ScopeDTO> scopes) {
    setClientId(client.getClientId());
    setIsSecretRequired(client.getIsSecretRequired());
    setIsAutoApprove(client.getIsAutoApprove());
    setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
    setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
    setRedirectUris(client.getRedirectUri() != null
        ? client.getRedirectUri().split(Client.REDIRECT_URI_SPLITTERATOR)
        : null);
    setGrantTypes(grantTypes);
    setGrantedAuthorities(grantedAuthorities);
    setScopes(scopes);
  }
}
