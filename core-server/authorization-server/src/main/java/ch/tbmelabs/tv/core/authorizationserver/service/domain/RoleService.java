package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;

public interface RoleService {

  Role findByName(String name);
}
