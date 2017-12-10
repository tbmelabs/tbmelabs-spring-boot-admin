package ch.tbmelabs.tv.core.authorizationserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.security.bruteforcing.BruteforceFilter;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.tv.shared.domain.authentication.logging.AuthenticationLog.AUTHENTICATION_STATE;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
  @Autowired
  private AuthenticationAttemptLogger authenticationAttemptLogger;

  @Autowired
  private BruteforceFilter bruteForceFilter;

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();

    authenticationAttemptLogger.logAuthenticationAttempt(AUTHENTICATION_STATE.OK, auth.getRemoteAddress(), new String(),
        event.getAuthentication().getName());

    bruteForceFilter.authenticationFromIpSucceed(auth.getRemoteAddress());
  }
}