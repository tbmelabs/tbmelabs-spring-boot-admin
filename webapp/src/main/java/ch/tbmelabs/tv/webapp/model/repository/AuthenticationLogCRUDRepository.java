package ch.tbmelabs.tv.webapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.tbmelabs.tv.webapp.model.AuthenticationLog;

@RepositoryRestResource(exported = false)
public interface AuthenticationLogCRUDRepository extends CrudRepository<AuthenticationLog, Long> {
}