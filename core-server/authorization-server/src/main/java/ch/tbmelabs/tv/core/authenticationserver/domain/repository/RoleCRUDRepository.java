package ch.tbmelabs.tv.core.authenticationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.shared.domain.authentication.user.Role;

@Repository
public interface RoleCRUDRepository extends CrudRepository<Role, Long> {
}