package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeCRUDRepository extends CrudRepository<Scope, Long> {

  Page<Scope> findAll(Pageable pageable);

  Optional<Scope> findByName(String name);
}
