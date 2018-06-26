package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAttemptLogger {

  private static final Logger LOGGER = LogManager.getLogger(AuthenticationAttemptLogger.class);

  private AuthenticationLogCRUDRepository authenticationLogRepository;

  private UserCRUDRepository userRepository;

  public AuthenticationAttemptLogger(
      AuthenticationLogCRUDRepository authenticationLogCRUDRepository,
      UserCRUDRepository userCRUDRepository) {
    this.authenticationLogRepository = authenticationLogCRUDRepository;
    this.userRepository = userCRUDRepository;
  }

  public void logAuthenticationAttempt(AUTHENTICATION_STATE state, String ip, String message,
      String username) {
    LOGGER.debug("Authentication attempt from " + ip + " with state " + state.name());

    Optional<User> user;
    if (!(user = userRepository.findOneByUsernameIgnoreCase(username)).isPresent()) {
      LOGGER
          .warn("Invalid username \"" + username + "\" detected: Probably a bruteforcing attempt?");

      return;
    }

    authenticationLogRepository.save(new AuthenticationLog(state, ip, message, user.get()));
  }
}
