package ch.tbmelabs.tv.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.resource.authentication.logging.AuthenticationLog;

@Repository
public interface AuthenticationLogCRUDRepository extends CrudRepository<AuthenticationLog, Long> {
}