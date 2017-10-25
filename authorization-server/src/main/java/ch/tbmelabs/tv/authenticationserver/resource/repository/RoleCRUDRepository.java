package ch.tbmelabs.tv.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.resource.authentication.user.Role;

@Repository
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {
}