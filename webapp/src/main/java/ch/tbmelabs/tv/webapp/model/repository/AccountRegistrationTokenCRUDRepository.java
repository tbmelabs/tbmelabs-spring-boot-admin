package ch.tbmelabs.tv.webapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.AccountRegistrationToken;

@RepositoryRestResource(exported = false)
public interface AccountRegistrationTokenCRUDRepository extends CrudRepository<AccountRegistrationToken, Long> {
  public AccountRegistrationToken findByToken(String token);

  public AccountRegistrationToken findByAccount(Account account);
}