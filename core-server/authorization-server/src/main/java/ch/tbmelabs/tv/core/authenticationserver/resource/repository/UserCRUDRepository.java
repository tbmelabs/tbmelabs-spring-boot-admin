package ch.tbmelabs.tv.core.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.resource.authentication.user.User;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);
}