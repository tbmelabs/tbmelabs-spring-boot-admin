package ch.tbmelabs.tv.core.authorizationserver.service.domain.impl;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleCRUDRepository roleRepository;

  public RoleServiceImpl(RoleCRUDRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findByName(String name) {
    return roleRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(
        "The default " + Role.class + "'" + name + "' does not exist!"));
  }
}
