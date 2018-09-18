package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class PreAuthenticatedAuthenticationProviderImpl
  extends PreAuthenticatedAuthenticationProvider {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(PreAuthenticatedAuthenticationProviderImpl.class);

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
