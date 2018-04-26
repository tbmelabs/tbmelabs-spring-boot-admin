package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends AbstractBasicEntityDTO {

  private String clientId;
  private Boolean isSecretRequired = true;
  private Boolean isAutoApprove = false;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
  private String[] redirectUri;
  private Collection<GrantTypeDTO> grantTypes;
  private Collection<AuthorityDTO> grantedAuthorities;
  private Collection<ScopeDTO> scopes;

  public ClientDTO() {
  }

  public ClientDTO(Client client, ArrayList<GrantTypeDTO> grantTypes,
      ArrayList<AuthorityDTO> grantedAuthorities, ArrayList<ScopeDTO> scopes) {
    setClientId(client.getClientId());
    setIsSecretRequired(client.getIsSecretRequired());
    setIsAutoApprove(client.getIsAutoApprove());
    setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
    setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
    setRedirectUri(client.getRedirectUri() != null
        ? client.getRedirectUri().split(Client.REDIRECT_URI_SPLITTERATOR)
        : null);
    setGrantTypes(grantTypes);
    setGrantedAuthorities(grantedAuthorities);
    setScopes(scopes);
  }
}
