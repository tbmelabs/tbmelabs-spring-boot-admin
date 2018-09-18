package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
