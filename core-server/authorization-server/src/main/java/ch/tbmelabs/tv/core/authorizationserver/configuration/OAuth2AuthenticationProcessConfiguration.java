package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;

@Configuration
public class OAuth2AuthenticationProcessConfiguration {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Bean
  public OAuth2AuthenticationProcessingFilter bearerTokenFilter() {
    OAuth2AuthenticationProcessingFilter filter = new OAuth2AuthenticationProcessingFilter();

    filter.setAuthenticationManager(authenticationManager);

    return filter;
  }
}