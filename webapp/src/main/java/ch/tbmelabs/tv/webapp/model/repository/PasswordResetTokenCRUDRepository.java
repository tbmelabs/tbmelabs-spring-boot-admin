package ch.tbmelabs.tv.webapp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.PasswordResetToken;

@RepositoryRestResource(exported = false)
public interface PasswordResetTokenCRUDRepository extends CrudRepository<PasswordResetToken, Long> {
  public PasswordResetToken findByToken(String token);

  public PasswordResetToken findByAccount(Account account);
}