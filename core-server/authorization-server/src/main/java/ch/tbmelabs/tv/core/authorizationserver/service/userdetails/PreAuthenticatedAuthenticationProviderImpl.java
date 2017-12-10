package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class PreAuthenticatedAuthenticationProviderImpl extends PreAuthenticatedAuthenticationProvider {
  @Autowired
  private PreAuthenticationUserDetailsServiceImpl userDetailsService;

  @PostConstruct
  public void postConstruct() {
    super.setPreAuthenticatedUserDetailsService(userDetailsService);
  }
}