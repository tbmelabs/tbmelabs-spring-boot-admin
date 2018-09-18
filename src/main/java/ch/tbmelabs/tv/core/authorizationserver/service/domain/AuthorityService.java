package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;

public interface AuthorityService {

  Authority findByName(String name);
}
