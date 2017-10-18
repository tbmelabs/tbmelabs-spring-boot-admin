package ch.tbmelabs.tv.authenticationserver.security.logging.bruteforcefilter;

import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent arg0) {
    LogManager.getLogger().fatal("Authentication success registered!");
  }
}