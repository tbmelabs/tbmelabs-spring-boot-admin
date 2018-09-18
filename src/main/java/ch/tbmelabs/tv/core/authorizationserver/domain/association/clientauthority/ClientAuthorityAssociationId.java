package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import java.io.Serializable;
import lombok.Data;

@Data
public class ClientAuthorityAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Client client;
  private Authority authority;
}
