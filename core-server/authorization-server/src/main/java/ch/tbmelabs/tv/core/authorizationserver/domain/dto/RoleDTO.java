package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends AbstractBasicEntityDTO {

  private String name;

  @Setter(AccessLevel.NONE)
  private String authority;

  public void setName(String name) {
    this.name = name;
    this.authority = name;
  }
}
