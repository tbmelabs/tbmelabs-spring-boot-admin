package ch.tbmelabs.tv.authenticationserver.security.logging.bruteforcefilter;

import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    LogManager.getLogger().fatal("Authentication failure registered!");
  }
}