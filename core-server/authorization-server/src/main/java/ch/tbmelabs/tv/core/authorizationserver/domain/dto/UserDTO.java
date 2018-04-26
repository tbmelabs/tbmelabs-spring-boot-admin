package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends AbstractBasicEntityDTO {

  private String username;
  private String email;
  private Boolean isEnabled = false;
  private Boolean isBlocked = false;
  private String emailConfirmationToken;
  private Collection<RoleDTO> roles;

  public UserDTO() {
  }

  public UserDTO(User user, ArrayList<RoleDTO> roles) {
    setUsername(user.getUsername());
    setEmail(user.getEmail());
    setIsEnabled(user.getIsEnabled());
    setIsBlocked(user.getIsBlocked());
    setEmailConfirmationToken(
        user.getEmailConfirmationToken() != null ? user.getEmailConfirmationToken().getTokenString()
            : null);
    setRoles(roles);
  }
}
