package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityDTO extends AbstractBasicEntityDTO {

  private String name;

  public void setName(String name) {
    this.name = name;
  }
}
