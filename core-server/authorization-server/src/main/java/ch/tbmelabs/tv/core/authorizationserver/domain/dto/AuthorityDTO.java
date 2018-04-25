package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityDTO extends AbstractBasicEntityDTO {

  private String name;
  private String authority;

  public void setAuthority(String name) {
    this.authority = Authority.ROLE_PREFIX + authority;
  }
}
