package ch.tbmelabs.tv.webapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.tbmelabs.tv.webapp.model.Role;

@RepositoryRestResource
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {
  public Role findByRoleName(String roleName);
}