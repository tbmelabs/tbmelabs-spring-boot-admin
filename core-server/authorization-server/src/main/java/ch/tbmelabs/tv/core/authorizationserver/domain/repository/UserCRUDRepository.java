package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {
  Page<User> findAll(Pageable pageable);

  Optional<User> findOneByUsername(String username);

  Optional<User> findOneByEmail(String email);

  @PreAuthorize("isAuthenticated()")
  @Query("SELECT user FROM User user WHERE user.username LIKE ?#{principal.username}")
  User findCurrentUser();
}