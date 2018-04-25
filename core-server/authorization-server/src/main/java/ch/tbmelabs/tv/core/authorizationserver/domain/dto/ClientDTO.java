package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

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
}
