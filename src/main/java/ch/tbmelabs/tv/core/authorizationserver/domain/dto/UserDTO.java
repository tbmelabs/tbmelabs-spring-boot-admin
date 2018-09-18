package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO extends AbstractBasicEntityDTO {

  private String username;
  private @JsonProperty(access = Access.WRITE_ONLY)
  String password;
  private @JsonProperty(access = Access.WRITE_ONLY)
  String confirmation;
  private String email;
  private Boolean isEnabled = false;
  private Boolean isBlocked = false;
  private Set<RoleDTO> roles;
}
