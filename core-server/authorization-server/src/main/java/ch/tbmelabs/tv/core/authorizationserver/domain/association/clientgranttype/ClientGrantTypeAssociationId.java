package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

import java.io.Serializable;
import lombok.Data;

@Data
public class ClientGrantTypeAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long clientId;
  private Long clientGrantTypeId;
}
