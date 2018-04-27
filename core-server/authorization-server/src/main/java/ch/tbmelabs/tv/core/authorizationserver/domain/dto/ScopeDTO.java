package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScopeDTO extends AbstractBasicEntityDTO {

  private String name;

  public ScopeDTO(String name) {
    setName(name);
  }
}
