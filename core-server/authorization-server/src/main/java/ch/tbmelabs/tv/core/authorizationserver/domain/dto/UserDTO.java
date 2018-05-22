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
public class UserDTO extends AbstractBasicEntityDTO {

  private String username;
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;
  @JsonProperty(access = Access.WRITE_ONLY)
  private String confirmation;
  private String email;
  private Boolean isEnabled = false;
  private Boolean isBlocked = false;
  private Set<RoleDTO> roles;
}
