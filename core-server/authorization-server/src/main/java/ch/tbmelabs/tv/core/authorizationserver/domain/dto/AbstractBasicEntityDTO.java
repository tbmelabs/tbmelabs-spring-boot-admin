package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import java.util.Date;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractBasicEntityDTO {

  private Long id;
  private Date created;
  private Date lastUpdated;
}
