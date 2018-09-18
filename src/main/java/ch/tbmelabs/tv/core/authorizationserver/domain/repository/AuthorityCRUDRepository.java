package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityCRUDRepository extends CrudRepository<Authority, Long> {

  Page<Authority> findAll(Pageable pageable);

  Optional<Authority> findByName(String name);
}
