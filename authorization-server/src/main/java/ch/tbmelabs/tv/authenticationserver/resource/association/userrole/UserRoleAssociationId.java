package ch.tbmelabs.tv.authenticationserver.resource.association.userrole;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleAssociationId implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long userId;
  private Long userRoleId;
}