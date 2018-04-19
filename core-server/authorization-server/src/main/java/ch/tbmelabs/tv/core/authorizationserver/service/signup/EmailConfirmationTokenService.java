package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.EmailConfirmationTokenCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.exception.EmailConfirmationTokenNotFoundException;

@Service
public class EmailConfirmationTokenService {

  private static final Logger LOGGER = LogManager.getLogger(EmailConfirmationTokenService.class);

  @Autowired
  private EmailConfirmationTokenCRUDRepository emailConfirmationTokenRepository;

  public String createUniqueEmailConfirmationToken(User user) {
    LOGGER.info("Creating unique confirmation token");

    String token = UUID.randomUUID().toString();

    if (emailConfirmationTokenRepository.findOneByTokenString(token).isPresent()) {
      return createUniqueEmailConfirmationToken(user);
    }

    LOGGER.debug("Created token " + token);

    return emailConfirmationTokenRepository.save(new EmailConfirmationToken(token, user))
        .getTokenString();
  }

  public void confirmRegistration(String token) throws EmailConfirmationTokenNotFoundException {
    LOGGER.info("User confirmation request with token " + token);

    Optional<EmailConfirmationToken> emailConfirmationToken;
    if (!(emailConfirmationToken = emailConfirmationTokenRepository.findOneByTokenString(token))
        .isPresent()) {
      LOGGER.warn("Unable to find " + EmailConfirmationToken.class + ": " + token);

      throw new EmailConfirmationTokenNotFoundException(token);
    }

    User user = emailConfirmationToken.get().getUser();
    user.setIsEnabled(true);

    emailConfirmationTokenRepository.delete(emailConfirmationToken.get());

    LOGGER.debug("User " + user.getUsername() + " confirmed registration with token " + token);
  }
}
