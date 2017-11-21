package ch.tbmelabs.tv.core.authenticationserver.security.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authenticationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authenticationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.domain.authentication.logging.AuthenticationLog;
import ch.tbmelabs.tv.shared.domain.authentication.logging.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.shared.domain.authentication.user.User;

@Component
public class AuthenticationAttemptLogger {
  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  public void logAuthenticationAttempt(AUTHENTICATION_STATE state, String ip, String message, String username) {
    User user;
    if ((user = userRepository.findByUsername(username)) == null) {
      // This is rather a brute-force than a login attempt
      return;
    }

    authenticationLogRepository.save(new AuthenticationLog(state, ip, message, user));
  }
}