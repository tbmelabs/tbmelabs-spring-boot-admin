package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class PreAuthenticatedAuthenticationProviderImpl
    extends PreAuthenticatedAuthenticationProvider {

  private static final Logger LOGGER =
      LogManager.getLogger(PreAuthenticatedAuthenticationProviderImpl.class);

  private PreAuthenticationUserDetailsServiceImpl userDetailsService;

  public PreAuthenticatedAuthenticationProviderImpl(
      PreAuthenticationUserDetailsServiceImpl preAuthenticationUserDetailsService) {
    this.userDetailsService = preAuthenticationUserDetailsService;
  }

  @PostConstruct
  public void initBean() {
    LOGGER.info("Initializing..");

    super.setPreAuthenticatedUserDetailsService(userDetailsService);
  }
}
