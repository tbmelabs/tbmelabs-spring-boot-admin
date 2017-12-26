package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope;

import java.io.Serializable;

public class ClientScopeAssociationId implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long clientId;
  private Long clientScopeId;
}