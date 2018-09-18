package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;

public interface GrantTypeService {

  GrantType findByName(String name);
}
