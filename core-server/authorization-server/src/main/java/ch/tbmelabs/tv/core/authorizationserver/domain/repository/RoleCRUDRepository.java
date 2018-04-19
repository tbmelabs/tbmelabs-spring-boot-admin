package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;

@Repository
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {
  Optional<Role> findOneByName(String name);
}
