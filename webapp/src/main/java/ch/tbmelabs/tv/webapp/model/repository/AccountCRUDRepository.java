package ch.tbmelabs.tv.webapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.tbmelabs.tv.webapp.model.Account;

@RepositoryRestResource(exported = false)
public interface AccountCRUDRepository extends CrudRepository<Account, Long> {
  public Account findByUsername(String username);

  public Account findByEmail(String email);
}