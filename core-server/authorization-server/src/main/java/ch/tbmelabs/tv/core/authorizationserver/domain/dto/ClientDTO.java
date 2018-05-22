package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends AbstractBasicEntityDTO {

  private String clientId;
  @JsonProperty(access = Access.WRITE_ONLY)
  private String secret;
  private Boolean isSecretRequired = true;
  private Boolean isAutoApprove = false;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
  private String[] redirectUris;
  private Set<GrantTypeDTO> grantTypes;
  private Set<AuthorityDTO> grantedAuthorities;
  private Set<ScopeDTO> scopes;
}
