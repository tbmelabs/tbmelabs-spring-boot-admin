package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GrantTypeDTO extends AbstractBasicEntityDTO {

  private String name;
}
