package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {

  Page<User> findAll(Pageable pageable);

  Optional<User> findOneByUsernameIgnoreCase(String username);

  Optional<User> findOneByEmailIgnoreCase(String email);

  @PreAuthorize("isAuthenticated()")
  @Query("SELECT u FROM User u WHERE u.username LIKE ?#{principal.username}")
  User findCurrentUser();

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE User u SET u.isEnabled = true WHERE u.id = :#{#user.id}")
  void updateUserSetIsEnabledTrue(@Param("user") User user);
}
