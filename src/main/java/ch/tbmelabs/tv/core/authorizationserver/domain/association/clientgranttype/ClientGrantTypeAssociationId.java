package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import java.io.Serializable;
import lombok.Data;

@Data
public class ClientGrantTypeAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Client client;
  private GrantType grantType;
}
