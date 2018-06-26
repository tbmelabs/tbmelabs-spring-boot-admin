package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.EmailConfirmationTokenCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.exception.EmailConfirmationTokenNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EmailConfirmationTokenService {

  private static final Logger LOGGER = LogManager.getLogger(EmailConfirmationTokenService.class);

  private EmailConfirmationTokenCRUDRepository emailConfirmationTokenRepository;

  private UserCRUDRepository userRepository;

  public EmailConfirmationTokenService(
      EmailConfirmationTokenCRUDRepository emailConfirmationTokenCRUDRepository,
      UserCRUDRepository userCRUDRepository) {
    this.emailConfirmationTokenRepository = emailConfirmationTokenCRUDRepository;
    this.userRepository = userCRUDRepository;
  }

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

    final User user = emailConfirmationToken.get().getUser();
    userRepository.updateUserSetIsEnabledTrue(user);

    // TODO: This is not affected?
    emailConfirmationTokenRepository.delete(emailConfirmationToken.get());

    LOGGER.debug("User " + user.getUsername() + " confirmed registration with token " + token);
  }
}
