package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;

public interface ScopeService {

  Scope findByName(String name);
}
