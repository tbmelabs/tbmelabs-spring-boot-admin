package ch.tbmelabs.tv.core.authenticationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.shared.domain.authentication.logging.AuthenticationLog;

@Repository
public interface AuthenticationLogCRUDRepository extends CrudRepository<AuthenticationLog, Long> {
}