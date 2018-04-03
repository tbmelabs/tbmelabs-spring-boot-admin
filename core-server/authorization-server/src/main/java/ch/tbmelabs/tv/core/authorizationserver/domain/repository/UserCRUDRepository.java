package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {
  Page<User> findAll(Pageable pageable);

  Optional<User> findOneByUsername(String username);

  Optional<User> findOneByEmail(String email);
}