package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GrantTypeDTO extends AbstractBasicEntityDTO {

  private String name;

  public GrantTypeDTO(String name) {
    setName(name);
  }
}
