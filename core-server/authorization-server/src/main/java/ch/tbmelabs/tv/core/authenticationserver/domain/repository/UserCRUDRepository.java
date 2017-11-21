package ch.tbmelabs.tv.core.authenticationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.shared.domain.authentication.user.User;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);
}