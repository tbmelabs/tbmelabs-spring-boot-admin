package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAttemptLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationAttemptLogger.class);

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
    LOGGER.debug("Authentication attempt from {} with state {}", ip, state.name());

    userRepository.findByUsernameIgnoreCase(username).ifPresent(
        (user) -> authenticationLogRepository
            .save(new AuthenticationLog(state, ip, message, user)));
  }
}
