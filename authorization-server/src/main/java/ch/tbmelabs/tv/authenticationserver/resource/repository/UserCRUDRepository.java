package ch.tbmelabs.tv.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.authenticationserver.resource.user.User;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);
}