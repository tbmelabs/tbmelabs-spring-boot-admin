package ch.tbmelabs.tv.authenticationserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.authenticationserver.security.bruteforcing.BruteforceFilter;
import ch.tbmelabs.tv.authenticationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.tv.resource.authentication.logging.AuthenticationLog.AUTHENTICATION_STATE;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
  @Autowired
  private AuthenticationAttemptLogger authenticationAttemptLogger;

  @Autowired
  private BruteforceFilter bruteForceFilter;

  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();

    authenticationAttemptLogger.logAuthenticationAttempt(AUTHENTICATION_STATE.NOK, auth.getRemoteAddress(),
        event.getException().getLocalizedMessage(), event.getAuthentication().getName());

    bruteForceFilter.authenticationFromIpFailed(auth.getRemoteAddress());
  }
}