package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationTokenCRUDRepository
    extends CrudRepository<EmailConfirmationToken, Long> {

  Optional<EmailConfirmationToken> findOneByTokenString(String tokenString);
}
