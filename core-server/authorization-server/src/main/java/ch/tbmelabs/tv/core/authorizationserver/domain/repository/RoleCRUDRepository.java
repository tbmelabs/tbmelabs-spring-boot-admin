package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;

import java.util.Optional;

@Repository
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {
  Optional<Role> findOneByName(String name);
}